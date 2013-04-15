package org.wintrisstech.erik.sprinkler;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ScheduleFormServlet extends HttpServlet {
	private static final String ATT_MASTER_ON_OFF = "master_on_off";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		RequestDispatcher view = request.getRequestDispatcher("/view/form.jsp");
		response.setContentType("text/html");
		view.forward(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Enumeration<String> parameterNames = request.getParameterNames();
		List<String> parameterNameList = new ArrayList<String>();

		while (parameterNames.hasMoreElements()) {
			parameterNameList.add(parameterNames.nextElement());
		}
		Collections.sort(parameterNameList);
		PrintWriter printer = response.getWriter();

		for (String parameterName : parameterNameList) {
			printer.print(parameterName);
			printer.println(" = " + request.getParameter(parameterName));
		}

		// create the attributes
		for (String s : parameterNameList) {
			String attributeName = "ATT_" + s.toUpperCase();
			request.setAttribute(attributeName, request.getParameter(s));
		}
		// validate all parameters
		// Pattern timeParameterPattern =
		// Pattern.compile("\\d\\d_time_(on|off)");
		// for (String s : parameterNameList) {
		// Matcher m = timeParameterPattern.matcher(s);
		// if (m.matches()) {
		// boolean timeValueOK = validateTimeParameter(s,
		// request.getParameter(s));
		// if (!timeValueOK) {
		// printer.println(s + " = " + request.getParameter(s));
		// }
		// }
		//
		// }
		ScheduleErrorChecker scheduleErrorChecker = new ScheduleErrorChecker();
		scheduleErrorChecker.checkForErrors(parameterNameList, request);
		if (scheduleErrorChecker.hasError()) {
			for (String s : scheduleErrorChecker.getErrorMessages()) {
				printer.println("<br/>");
				printer.println(s);

			}
			RequestDispatcher view = request
					.getRequestDispatcher("/view/schedule_error_page.jsp");
			view.forward(request, response);
			

		} else {

		}
		// store all parameter values in the data store
		for (String prefix : new String[] { "00", "01", "02" }) {
			if (isDefinedSchedule(prefix, request)) {
				Schedule schedule = new Schedule();
				schedule.setTime(request.getParameter(prefix + "_time_on"),
						request.getParameter(prefix + "_time_off"));
				boolean[] DOW = new boolean[7];
				for (int i = 0; i < 7; i++) {
					DOW[i] = request.getParameter(prefix + "_dow" + i) != null
							&& request.getParameter(prefix + "_dow" + i)
									.equals("on");
				}
				schedule.setDOW(DOW);
				boolean[] zones = new boolean[6];
				for (int i = 0; i < 6; i++) {
					zones[i] = request.getParameter(prefix + "_zone0" + i) != null
							&& request.getParameter(prefix + "_zone0" + i)
									.equals("on");
				}
				schedule.setZones(zones);
				schedule.setOnOff(request.getParameter(prefix + "_on_off") != null
						&& request.getParameter(prefix + "_on_off")
								.equals("on"));
			}
		}

	}

	private boolean isDefinedSchedule(String prefix, HttpServletRequest request) {
		String timeOn = request.getParameter(prefix + "_time_on");
		String timeOff = request.getParameter(prefix + "_time_off");

		return timeOn != null && !timeOn.isEmpty() && timeOff != null
				&& !timeOff.isEmpty();
	}

	private boolean validateTimeParameter(String parameterName,
			String parameterValue) {
		Pattern timeParameterPattern = Pattern.compile("\\d\\d_time_(on|off)");
		Pattern timeValuePattern = Pattern
				.compile("(\\d?\\d):(\\d\\d)(:(\\d\\d))?");

		Matcher m = timeParameterPattern.matcher(parameterName);
		if (m.matches()) {
			Matcher timeMatcher = timeValuePattern.matcher(parameterValue);
			if (timeMatcher.matches()) {
				int hours = 0;
				int minutes = 0;
				int seconds = 0;
				hours = Integer.parseInt(timeMatcher.group(1));
				minutes = Integer.parseInt(timeMatcher.group(2));
				if (timeMatcher.group(4) != null) {
					seconds = Integer.parseInt(timeMatcher.group(4));
				}
				if (0 <= hours && hours <= 23 && 0 <= minutes && minutes <= 59
						&& 0 <= seconds && seconds <= 59) {
					return true;
				}
			}

		}

		return false;

	}

}
