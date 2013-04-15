package org.wintrisstech.erik.sprinkler;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ScheduleFormServlet extends HttpServlet {

	private static final String TIME_ERROR = "error";

	/**
	 * @param args
	 */

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		RequestDispatcher view = request
				.getRequestDispatcher("/view/form.jsp");
		response.setContentType("text/html");
		view.forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		List<String> timeParameterNames = new ArrayList<String>();
		Pattern timeParameterNamePattern = Pattern.compile("time\\d{4}");

		@SuppressWarnings("unchecked")
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String name = parameterNames.nextElement();
			Matcher m = timeParameterNamePattern.matcher(name);
			if (m.matches()) {
				timeParameterNames.add(name);
			}
		}
		boolean isError = false;
		for (String p : timeParameterNames) {
			String error = validateTimeParameter(request.getParameter(p));
			if (error != null) {
				isError = true;
				break;
			}
		}

		response.setContentType("text/plain");
		response.getWriter().println(
				"This form has " + (isError ? "errors." : "no errors."));
		RequestDispatcher view = request
				.getRequestDispatcher("/view/signup.jsp");
		view.forward(request, response);
	}

	private String validateTimeParameter(String parameter) {
		Pattern timePattern = Pattern.compile("(\\d\\d):(\\d\\d)(:(\\d\\d))?");
		Matcher m = timePattern.matcher(parameter);
		if (m.matches()) {
			int hours = Integer.parseInt(m.group(1));
			int minutes = Integer.parseInt(m.group(2));
			int seconds = 0;

			if (m.groupCount() > 2) {
				seconds = Integer.parseInt(m.group(3));
			}
			if (0 <= hours && hours < 24 && 0 <= minutes && minutes < 60
					&& 0 <= seconds && seconds < 60) {
				return null;
			}
		}
		return TIME_ERROR;
	}
}
