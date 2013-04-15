package org.wintrisstech.erik.sprinkler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 * A servlet that handles requests for the welcome page.
 * 
 * @author ecolban
 * 
 */
@SuppressWarnings("serial")
public class ScheduleIdServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(ScheduleIdServlet.class
			.getName());

	/**
	 * The name of the parameter that holds the user name.
	 */
	public final static String SPRINKLER_NAME_PARAM = "sprinklername";

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Cookie[] cookies = request.getCookies();
		long id = User.getId(cookies);
		User user = UserDataAccess.getUserById(id);
		if (user != null) {
			String scheduleId = user.getScheduleId();
			PrintWriter out = response.getWriter();
			out.println(scheduleId);
			// response.setHeader("ContentType", "application/octet-stream");
			response.setHeader("Content-Disposition", "attachment");
		} else {
			logger.log(Level.WARNING, "Attempt to download id file when not logged in.");
			response.sendRedirect("/");
		}
	}

}
