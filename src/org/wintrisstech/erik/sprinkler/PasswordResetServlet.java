package org.wintrisstech.erik.sprinkler;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 * A servlet that handles password reset requests.
 * 
 * @author ecolban
 * 
 */
@SuppressWarnings("serial")
public class PasswordResetServlet extends HttpServlet {
	private static final long TIMESTAMP_UNIT = 24L * 3600000L; // 1 day
	private static final String LINK_SECRET = "L1nK S3crt"; // Not very smart!!

	private static final Logger logger = Logger
			.getLogger(PasswordResetServlet.class.getName());

	private static final Pattern URI_PATTERN = Pattern
			.compile("/reset/(\\d+)_([a-z2-7]+)");

	private final static String VERIFY_ERROR_MESSAGE = "Your passwords didn't match";

	/**
	 * The name of the parameter that holds the password.
	 */
	public final static String PASSWORD_PARAM = "password";
	/**
	 * The name of the parameter that holds the verified password.
	 */
	public final static String VERIFY_PARAM = "verify";

	/**
	 * The name of an attribute used to hold the error message.
	 */
	public final static String ERROR_ATTRIBUTE = "error";

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			long id = getId(request);
			if (id > 0) {
				User user = UserDataAccess.getUserById(id);
				if (user != null) {
					RequestDispatcher view = request
							.getRequestDispatcher("/view/password_reset.jsp");
					request.setAttribute("username", user.getUserName());
					response.setContentType("text/html");
					view.forward(request, response);
					return;
				}
			}
		} catch (InvalidKeyException e) {
		} catch (NoSuchAlgorithmException e) {
		}
		response.sendRedirect("/");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String password = request.getParameter(PASSWORD_PARAM);
		String verify = request.getParameter(VERIFY_PARAM);

		String errorMessage = null;
		String userName = null;
		try {
			long id = getId(request);
			if ((errorMessage = validatePassword(password)) == null
					&& (errorMessage = validateVerify(password, verify)) == null) {
				if (id > 0L) {
					UserDataAccess.setPassword(id, password);
					Cookie cookie = User.getCookie(id);
					response.addCookie(cookie);
					response.sendRedirect("/");
					return;
				}
			} else {
				User user = UserDataAccess.getUserById(id);
				if (user != null) {
					userName = user.getUserName();
					request.setAttribute("username", userName);
				}
			}
		} catch (InvalidKeyException e) {
		} catch (NoSuchAlgorithmException e) {
		}
		response.setContentType("text/html");
		request.setAttribute(ERROR_ATTRIBUTE, errorMessage);
		RequestDispatcher view = request
				.getRequestDispatcher("/view/password_reset.jsp");
		view.forward(request, response);
	}

	private long getId(HttpServletRequest request) throws InvalidKeyException,
			NoSuchAlgorithmException {

		String uri = request.getRequestURI();
		if (uri != null) {
			Matcher m = URI_PATTERN.matcher(uri);
			if (m.matches()) {
				long id = Long.parseLong(m.group(1));
				if (checkPasswordResetLink(id, uri)) {
					return id;
				}
			}
		}
		return 0;
	}

	/**
	 * Checks if a password is valid, and if it is not, returns an error
	 * message.
	 * 
	 * @param password
	 *            the password to check.
	 * @return an error message if the password is not valid, or null if it is
	 *         valid.
	 */
	private String validatePassword(String password) {
		if (User.isValidPassword(password)) {
			return null;
		} else {
			return User.PASSWORD_ERROR_MESSAGE;
		}
	}

	/**
	 * Checks if the two passwords provided are equal
	 * 
	 * @param password
	 *            the password
	 * @param verify
	 *            the password verification
	 * @return true if the two passwords are equal
	 */
	private String validateVerify(String password, String verify) {
		// Ensure that password is not when this method is called.
		if (!password.equals(verify)) {
			return VERIFY_ERROR_MESSAGE;
		} else {
			return null;
		}
	}

	public static String getPasswordResetLink(long id)
			throws InvalidKeyException, NoSuchAlgorithmException {
		long timestamp = System.currentTimeMillis() / TIMESTAMP_UNIT;
		String hmac = getHmac(id, timestamp);
		return "/reset/" + id + "_" + hmac;

	}

	private static String getHmac(long id, long timestamp)
			throws InvalidKeyException, NoSuchAlgorithmException {
		return Crypto.encode32(Long.toString(id), getKey(timestamp))
				.substring(0, 52).toLowerCase();
	}

	private static boolean checkPasswordResetLink(long id2, String uri) {
		long timestamp = System.currentTimeMillis() / TIMESTAMP_UNIT;
		Matcher m = URI_PATTERN.matcher(uri);
		if (m.matches()) {
			try {
				long id = Long.parseLong(m.group(1));
				if (id != id2) {
					return false;
				}
				String hmac = m.group(2);
				return hmac.equals(getHmac(id, timestamp))
						|| hmac.equals(getHmac(id, timestamp - 1));

			} catch (NumberFormatException e) {
			} catch (InvalidKeyException e) {
			} catch (NoSuchAlgorithmException e) {
			}
		}
		return false;
	}

	private static byte[] getKey(long timestamp) {
		return (LINK_SECRET + Long.toHexString(timestamp)).getBytes();
	}

}
