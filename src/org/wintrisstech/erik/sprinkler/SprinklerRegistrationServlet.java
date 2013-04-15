package org.wintrisstech.erik.sprinkler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 * A servlet that handles requests for the welcome page.
 * 
 * @author ecolban
 * 
 */
@SuppressWarnings("serial")
public class SprinklerRegistrationServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(WelcomeServlet.class
			.getName());

	/**
	 * The name of an attribute used to hold the user name.
	 */
	public final static String USERNAME_ATTRIBUTE = "username";
	public final static String SPRINKLER_NAME_ATTRIBUTE = "sprinklername";
	/**
	 * The name of the parameter that holds the user name.
	 */
	public final static String SPRINKLER_NAME_PARAM = "sprinklername";

	private static final String ERROR_MESSAGE = "That's not a valid sprinkler name. "
			+ "Sprinkler names must be 3 to 15 characters long and may only contain letters, "
			+ "digits and underscores.";

	private static final String ERROR_ATTRIBUTE = "error";

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Cookie[] cookies = request.getCookies();
		long id = User.getId(cookies);
		User user = UserDataAccess.getUser(id);
		if (user != null) {
			String userName = user.getUserName();
			if (userName != null) {
				request.setAttribute(USERNAME_ATTRIBUTE, "" + userName);
			}
			String sprinklerName = request.getParameter(SPRINKLER_NAME_PARAM);
			if (sprinklerName != null) {

				request.setAttribute(SPRINKLER_NAME_ATTRIBUTE, ""
						+ sprinklerName);
			}
			RequestDispatcher view;
			if (userName == null) {
				view = request.getRequestDispatcher("/view/welcome.jsp");
			} else {
				view = request.getRequestDispatcher("/view/sprinkler.jsp");
			}
			response.setContentType("text/html");
			view.forward(request, response);
		} else {
			response.sendRedirect("/");
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Cookie[] cookies = request.getCookies();
		long id = User.getId(cookies);
		User user = UserDataAccess.getUser(id);
		String sprinklerName = request.getParameter(SPRINKLER_NAME_PARAM);
		String userName = null;
		String errorMessage = null;
		if (user != null) {
			userName = user.getUserName();
			if (userName != null) {
				request.setAttribute(USERNAME_ATTRIBUTE, "" + userName);
			}
			if (sprinklerName != null) {
				// validate the sprinkler name
				if ((errorMessage = validateSprinklerName(sprinklerName)) == null) {
//					try {
						user.setSprinklerName(sprinklerName);
						UserDataAccess.updateUser(user);
//						Cookie cookie = User.getCookie(id);
//						response.addCookie(cookie);
						response.sendRedirect("/");
						return;
//					} catch (InvalidKeyException e) {
//					} catch (NoSuchAlgorithmException e) {
//					}
				}
			}
		}
		logger.log(Level.WARNING, "Sprinkler name {0} was not saved.",
				sprinklerName);
		response.setContentType("text/html");
		request.setAttribute(USERNAME_ATTRIBUTE, userName);
		request.setAttribute(SPRINKLER_NAME_ATTRIBUTE, sprinklerName);
		request.setAttribute(ERROR_ATTRIBUTE, errorMessage);
		RequestDispatcher view = request
				.getRequestDispatcher("/view/sprinkler.jsp");
		view.forward(request, response);

	}

	/**
	 * Checks if a user name is valid, and if it is not return an error message.
	 * 
	 * @param sprinklerName
	 *            the user name to check.
	 * @return an error message if the user name is not valid, or null if it is
	 *         valid.
	 */
	private String validateSprinklerName(String sprinklerName) {
		if (User.isValidUsername(sprinklerName)) {
			return null;
		} else {
			return ERROR_MESSAGE;
		}
	}
}
