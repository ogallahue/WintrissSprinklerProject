package org.wintrisstech.erik.sprinkler;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;

import org.apache.commons.codec.binary.Base32;

/**
 * A utility class particularly related to a user.
 * 
 * @author ecolban
 * 
 */
public class User implements Serializable {

	private static final Logger logger = Logger
			.getLogger(User.class.getName());

	private static final long serialVersionUID = 1L;

	private final static Pattern USERNAME_PATTERN = Pattern
			.compile("^\\w{3,15}$");

	public final static String USERNAME_ERROR_MESSAGE = "That's not a valid username. "
			+ "User names must be 3 to 15 characters long and may only contain letters, "
			+ "digits and underscores.";

	private final static Pattern PASSWORD_PATTERN = Pattern
			.compile("^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,15})$");

	public final static String PASSWORD_ERROR_MESSAGE = "That's not a valid password. "
			+ "Passwords must be 6-15 characters long, must contain at least one lower case letter, "
			+ "one upper case letter, and one digit.";

	private final static Pattern EMAIL_PATTERN = Pattern
			.compile("^\\w+(\\.\\w+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	public final static String EMAIL_ERROR_MESSAGE = "That's not a valid email.";

	public final static String COOKIE_NAME = "user";

	public static boolean isValidPassword(String password) {
		Matcher m = PASSWORD_PATTERN.matcher(password);
		return m.matches();
	}

	private final String userName;
	private final String email;
	private String sprinklerName;
	private String scheduleId;
	private final long id;

	/**
	 * Package private constructor. Intended to be used by the UserDataAccess.
	 * It is assumed that all the arguments have been validated.
	 * 
	 * @param userName
	 * @param email
	 * @param sprinklerName
	 * @param schedule
	 */
	User(long id, String userName, String email, String sprinklerName,
			String schedule) {
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.sprinklerName = sprinklerName;
		this.scheduleId = schedule;
	}

	public static boolean isValidUsername(String username) {
		Matcher m = USERNAME_PATTERN.matcher(username);
		return m.matches();
	}

	public static boolean isValidEmail(String email) {
		Matcher m = EMAIL_PATTERN.matcher(email);
		return m.matches();
	}

	public static long getId(Cookie[] cookies) {
		String value = CookieEncoder.getCookieValue(cookies, COOKIE_NAME);
		try {
			long id = Long.parseLong(value);
			return id;
		} catch (NumberFormatException ex) {
		}
		return 0;
	}

	public static Cookie getCookie(long id) throws InvalidKeyException,
			NoSuchAlgorithmException {
		Cookie cookie = new Cookie(User.COOKIE_NAME, "" + id);
		CookieEncoder.encode(cookie);
		cookie.setPath("/");
		return cookie;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the sprinklerName
	 */
	public String getSprinklerName() {
		return sprinklerName;
	}

	/**
	 * @return the schedule
	 */
	public String getScheduleId() {
		return scheduleId;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	public void setSprinklerName(String sprinklerName) {
		this.sprinklerName = sprinklerName;
		SecureRandom random = new SecureRandom();
		byte[] randomBytes = new byte[20];
		random.nextBytes(randomBytes);
		this.scheduleId = new Base32().encodeAsString(randomBytes);
	}
}
