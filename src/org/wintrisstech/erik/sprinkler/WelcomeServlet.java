package org.wintrisstech.erik.sprinkler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.logging.Logger;
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
public class WelcomeServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(WelcomeServlet.class
			.getName());

	/**
	 * The name of an attribute used to hold the user name.
	 */
	public final static String USERNAME_ATTRIBUTE = "username";
	public final static String SPRINKLER_NAME_ATTRIBUTE = "sprinklername";
	public final static String SCHEDULE_ID_ATTRIBUTE = "scheduleId";

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Cookie[] cookies = request.getCookies();
		long id = User.getId(cookies);
		logger.log(Level.INFO, "Access by user with id = {0}", id);
		User user = UserDataAccess.getUser(id);
		if (user != null) {
			String userName = user.getUserName();
			if (userName != null) {
				logger.log(Level.INFO, "Access by user {0}", userName);
				request.setAttribute(USERNAME_ATTRIBUTE, "" + userName);
			} else {
				logger.log(Level.WARNING, "Received cookie with id = {0}. No user with this id.", id);
			}
			String sprinklerName = user.getSprinklerName();
			if (sprinklerName != null) {
				request.setAttribute(SPRINKLER_NAME_ATTRIBUTE, ""
						+ sprinklerName);
			}
		}

		RequestDispatcher view = request
				.getRequestDispatcher("/view/welcome.jsp");
		response.setContentType("text/html");
		view.forward(request, response);

	}
}
