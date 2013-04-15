package org.wintrisstech.erik.sprinkler;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

/**
 * Utility class to encode and decode cookies, and retrieving an encoded cookie
 * value.
 * 
 * @author ecolban
 * 
 */
public class CookieEncoder {

	private static final Logger logger = Logger.getLogger(CookieEncoder.class
			.getName());

	private static SecureRandom random = new SecureRandom();
	public static final long TIMESTAMP_UNIT = 24L * 3600000L; // 24 hours
	private static final String TIMESTAMP_ID = "timestamp_id";
	private static final String[] COOKIE_ID = { "cookie_id_1", "cookie_id_2" };
	private static final MemcacheService keyCache = MemcacheServiceFactory
			.getMemcacheService();

	/**
	 * Checks that a cookie is valid.
	 * 
	 * @param cookie
	 *            the given cookie
	 * @return true if the cookie is valid
	 */
	public static boolean check(Cookie cookie) {
		long timestamp = System.currentTimeMillis() / TIMESTAMP_UNIT;
		String[] value = cookie.getValue().split("\\|");
		String s = null;
		String hmac = null;
		if (value.length == 2 && (s = value[0]) != null
				&& (hmac = value[1]) != null) {
			try {
				return hmac.equals(getHmac(s, timestamp))
						|| hmac.equals(getHmac(s, timestamp - 1));
			} catch (InvalidKeyException e) {
			} catch (NoSuchAlgorithmException e) {
			}
		}
		return false;
	}

	private static byte[] getKey(long ts) {
		long now = System.currentTimeMillis() / TIMESTAMP_UNIT;
		long timestamp = getTimestamp();
		if (timestamp < now - 1) {
			byte[] tmp = new byte[20];
			random.nextBytes(tmp);
			setCookieSecret(0, tmp);
			tmp = new byte[20];
			random.nextBytes(tmp);
			setCookieSecret(1, tmp);
			CookieEncoder.setTimestamp(now);
		} else if (timestamp < now) {
			setCookieSecret(1, getCookieSecret(0));
			byte[] tmp = new byte[20];
			random.nextBytes(tmp);
			setCookieSecret(0, tmp);
			CookieEncoder.setTimestamp(now);
		}

		return ts < now - 1 ? null : ts < now ? getCookieSecret(1)
				: getCookieSecret(0);
	}

	/**
	 * Encodes a cookie. The result consists of the original cookie value
	 * followed by a HMAC.
	 * 
	 * @param cookie
	 *            the cookie to encode
	 * @throws NoSuchAlgorithmException
	 *             if the HMAC computation fails
	 * @throws InvalidKeyException
	 *             if the HMAC computation fails
	 */
	public static void encode(Cookie cookie) throws NoSuchAlgorithmException,
			InvalidKeyException {
		String value = cookie.getValue();
		long timestamp = System.currentTimeMillis() / TIMESTAMP_UNIT;
		cookie.setValue(value + "|" + getHmac(value, timestamp));
	}

	private static String getHmac(String value, long timestamp)
			throws InvalidKeyException, NoSuchAlgorithmException {
		return Crypto.encode64(value, getKey(timestamp));
	}

	private static String decode(Cookie cookie) {
		return cookie.getValue().split("\\|")[0];
	}

	public static String getCookieValue(Cookie[] cookies, String name) {
		if (name == null) {
			return null;
		}
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (name.equals(c.getName()) && CookieEncoder.check(c)) {
					return CookieEncoder.decode(c);
				}
			}
		}
		return null;
	}

	/**
	 * @param num
	 *            either 0 or 1
	 * @return the cookie secret
	 */
	private static byte[] getCookieSecret(int num) {
		return (byte[]) keyCache.get(COOKIE_ID[num]);
	}

	/**
	 * @param num
	 *            either 0 or 1
	 * @param cookieSecret
	 *            the cookie Secret to set
	 */
	private static void setCookieSecret(int num, byte[] cookieSecret) {
		keyCache.put(COOKIE_ID[num], cookieSecret);
	}

	/**
	 * Gets the time when the last cookie secret was generated
	 * 
	 * @return the timestamp
	 */
	private static long getTimestamp() {
		Long timestamp = (Long) keyCache.get(TIMESTAMP_ID);
		return timestamp == null ? 0L : timestamp.longValue();
	}

	/**
	 * Sets the timestamp for the cookie secret that was last generated
	 * 
	 * @param timestamp
	 *            the timestamp to set
	 */
	private static void setTimestamp(long timestamp) {
		keyCache.put(TIMESTAMP_ID, Long.valueOf(timestamp));
	}
}
