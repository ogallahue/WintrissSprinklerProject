package org.wintrisstech.erik.sprinkler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleErrorChecker {
	ArrayList<String> errorMessages = new ArrayList<String>();
	HashMap<String,String> fieldToUserMessage  =  new HashMap<String,String>();

	public ScheduleErrorChecker() {
		fieldToUserMessage.put("00_time_on", "Schedule 1 Time On");
		fieldToUserMessage.put("00_time_off", "Schedule 1 Time Off");
		fieldToUserMessage.put("01_time_on", "Schedule 2 Time On");
		fieldToUserMessage.put("01_time_off", "Schedule 2 Time Off");
		fieldToUserMessage.put("02_time_on", "Schedule 3 Time On");
		fieldToUserMessage.put("02_time_off", "Schedule 3 Time Off");
	}
	boolean hasError() {

		return !errorMessages.isEmpty();
	}

	List<String> getErrorMessages() {

		return errorMessages;
	}

	void checkForErrors(List<String> parameterNameList,
			HttpServletRequest request) {
		Pattern timeParameterPattern = Pattern.compile("\\d\\d_time_(on|off)");
		for (String s : parameterNameList) {
			Matcher m = timeParameterPattern.matcher(s);
			if (m.matches()) {
				boolean timeValueOK = validateTimeParameter(s,
						request.getParameter(s));
				if (!timeValueOK) {
					errorMessages.add( "Please change " + fieldToUserMessage.get(s) + " to follow the format HH:MM:SS");
				}
			}

		}
	}
	
	// Please change "attributeName" time code to look like this HH:MM:ss

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
