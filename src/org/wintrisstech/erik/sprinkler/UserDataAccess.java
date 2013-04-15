package org.wintrisstech.erik.sprinkler;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.ShortBlob;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

/**
 * A utility class used for accessing the data store to store and retrieve user
 * related information.
 * 
 * @author ecolban
 * 
 */
public class UserDataAccess {

	private static final DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	private static final MemcacheService keyCache = MemcacheServiceFactory
			.getMemcacheService();

	private static final Logger logger = Logger.getLogger(UserDataAccess.class
			.getName());

	private static final Random random = new Random();
	private final static String USER_KIND = "user_kind";
	private final static String USERNAME_PROPERTY = "username";
	private final static String SPRINKLER_NAME_PROPERTY = "sprinkler_name";
	private static final String SCHEDULE_ID_PROPERTY = "scheduleId";
	private final static String PASSWORD_PROPERTY = "password";
	private final static String SALT_PROPERTY = "salt";
	private final static String EMAIL_PROPERTY = "email";
	private final static int DEFAULT_SALT_LENGTH = 5;

	/**
	 * Add a user entity to the data store. Checks if a user with the same user
	 * name already exists in the data store and, if that is the case, does not
	 * enter the user.
	 * <p>
	 * The password is not stored in clear text in the data store. Instead, the
	 * HMAC generated from the password, and a randomly generated salt, which is
	 * used as the key for the HMAC computation, are stored.
	 * 
	 * @param username
	 *            the user's name
	 * @param password
	 *            the user's password
	 * @param email
	 *            the user's email
	 * @return the id of the inserted entity, or 0 if an entity with the same
	 *         user name already exists in the data store.
	 * @throws UnsupportedEncodingException
	 */
	public static long addUser(String username, String password, String email)
			throws UnsupportedEncodingException {
		Entity userAlreadyInDatastore = findUserEntity(username);
		if (userAlreadyInDatastore != null) {
			return 0L;
		}
		Entity entity = new Entity(USER_KIND);
		entity.setProperty(USERNAME_PROPERTY, username);
		byte[] salt = generateSalt();
		try {
			entity.setProperty(PASSWORD_PROPERTY,
					new ShortBlob(Crypto.computeHmac(password, salt)));
		} catch (InvalidKeyException e) {
			return 0L;
		} catch (NoSuchAlgorithmException e) {
			return 0L;
		}
		entity.setProperty(SALT_PROPERTY, new ShortBlob(salt));
		entity.setProperty(EMAIL_PROPERTY, email);
		entity.setProperty(SPRINKLER_NAME_PROPERTY, null);
		entity.setProperty(SCHEDULE_ID_PROPERTY, null);
		Key key = datastore.put(entity);
		long id = key.getId();
		User user = new User(id, username, email, null, null);
		keyCache.put(Long.valueOf(id), user);
		logger.log(Level.INFO, "Added new user to the data store.");
		return id;
	}

	/**
	 * Looks up the user with a given user name in the data store and, if found,
	 * checks if the given password is correct and, if it is, returns a new User
	 * instance. If the user is not found or the password is incorrect, null is
	 * returned.
	 * 
	 * @param userName
	 *            the given user name
	 * @param password
	 *            the given password
	 * @return a User instance
	 */
	public static User getUser(String userName, String password) {
		Entity user = findUserEntity(userName);
		if (user != null) {
			boolean passwordOK;
			try {
				passwordOK = Crypto.verifyHmac(password, ((ShortBlob) user
						.getProperty(SALT_PROPERTY)).getBytes(),
						((ShortBlob) user.getProperty(PASSWORD_PROPERTY))
								.getBytes());
				if (passwordOK) {
					return new User(user.getKey().getId(), userName,
							(String) user.getProperty(EMAIL_PROPERTY),
							(String) user.getProperty(SPRINKLER_NAME_PROPERTY),
							(String) user.getProperty(SCHEDULE_ID_PROPERTY));
				}
			} catch (InvalidKeyException e) {
			} catch (NoSuchAlgorithmException e) {
			} catch (IllegalStateException e) {
			}
		}
		return null;
	}

	/**
	 * Gets the User instance of the user with a given id.
	 * 
	 * @param id
	 *            the given id.
	 * @return the User instance or null if not found
	 */
	public static User getUserById(long id) {
		if (id <= 0) {
			return null;
		}
		User user = (User) keyCache.get(Long.valueOf(id));
		if (user != null) {
			return user;
		}
		logger.log(Level.INFO,
				"Querying the datastore for user name for user id = {0}", id);
		Key key = KeyFactory.createKey(USER_KIND, id);
		Entity userEntity = null;
		try {
			userEntity = datastore.get(key);
		} catch (EntityNotFoundException e) {
			return null;
		}
		if (userEntity != null) {
			user = new User(id,
					(String) userEntity.getProperty(USERNAME_PROPERTY),
					(String) userEntity.getProperty(EMAIL_PROPERTY),
					(String) userEntity.getProperty(SPRINKLER_NAME_PROPERTY),
					(String) userEntity.getProperty(SCHEDULE_ID_PROPERTY));
			keyCache.put(Long.valueOf(id), user);
			return user;
		} else {
			return null;
		}
	}

	/**
	 * Updates the properties of the user entity with the attributes of a given
	 * User instance
	 * 
	 * @param user
	 *            the User instance
	 */
	public static void updateUser(User user) {
		Key key = KeyFactory.createKey(USER_KIND, user.getId());
		Entity userEntity = null;
		try {
			userEntity = datastore.get(key);
		} catch (EntityNotFoundException e) {
			return;
		}
		if (userEntity != null) {
			userEntity.setProperty(EMAIL_PROPERTY, user.getEmail());
			userEntity.setProperty(SPRINKLER_NAME_PROPERTY,
					user.getSprinklerName());
			userEntity.setProperty(SCHEDULE_ID_PROPERTY, user.getScheduleId());
			datastore.put(userEntity);
			keyCache.put(Long.valueOf(user.getId()), user);
			logger.log(Level.INFO, "Updated user with id = {0}.", user.getId());
		}
	}

	/**
	 * Generates a 5 byte long random salt.
	 * 
	 * @return
	 */
	private static byte[] generateSalt() {
		byte[] result = new byte[DEFAULT_SALT_LENGTH];
		random.nextBytes(result);
		return result;
	}

	/**
	 * Looks up a user with a given user name
	 * 
	 * @param username
	 *            the given user name
	 * @return returns the found entity, if found, or null otherwise.
	 */
	private static Entity findUserEntity(String username) {
		logger.log(Level.INFO, "Querying the datastore for {0}.", username);
		Query query = new Query(USER_KIND);
		query.setFilter(new FilterPredicate(USERNAME_PROPERTY,
				FilterOperator.EQUAL, username));
		List<Entity> users = datastore.prepare(query).asList(
				FetchOptions.Builder.withLimit(1));
		if (users != null && users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	public static User getUserByEmail(String email) {
		logger.log(Level.INFO, "Querying the datastore for email = {0}.", email);
		Query query = new Query(USER_KIND);
		query.setFilter(new FilterPredicate(EMAIL_PROPERTY,
				FilterOperator.EQUAL, email));
		List<Entity> users = datastore.prepare(query).asList(
				FetchOptions.Builder.withLimit(1));
		if (users != null && users.size() > 0) {
			Entity userEntity = users.get(0);
			if (userEntity != null) {
				User user = new User((long) userEntity.getKey().getId(),
						(String) userEntity.getProperty(USERNAME_PROPERTY),
						(String) userEntity.getProperty(EMAIL_PROPERTY),
						(String) userEntity
								.getProperty(SPRINKLER_NAME_PROPERTY),
						(String) userEntity.getProperty(SCHEDULE_ID_PROPERTY));
				return user;
			}
		}
		return null;
	}

	public static void setPassword(long id, String password) 
			throws UnsupportedEncodingException {
		
		Key key = KeyFactory.createKey(USER_KIND, id);
		Entity entity = null;
		try {
			entity = datastore.get(key);
		} catch (EntityNotFoundException e) {
			return;
		}
		byte[] salt = generateSalt();
		try {
			entity.setProperty(PASSWORD_PROPERTY,
					new ShortBlob(Crypto.computeHmac(password, salt)));
			entity.setProperty(SALT_PROPERTY, new ShortBlob(salt));
			key = datastore.put(entity);
			logger.log(Level.INFO, "Updated the password of user with id = {0}", id);
		} catch (InvalidKeyException e) {
			return;
		} catch (NoSuchAlgorithmException e) {
			return ;
		}
	}

}