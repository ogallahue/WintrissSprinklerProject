package org.wintrisstech.erik.sprinkler;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;

public class Hacker {

	private static final String TIME_ERROR = "Incorrect time format";

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public static void main(String[] args) {
		Hacker h = new Hacker();
		String error = h.validateTimeParameter("06:03:60");
		System.out.println(error);
	}

	private String validateTimeParameter(String parameter) {
		Pattern timePattern = Pattern.compile("(\\d\\d):(\\d\\d)(:(\\d\\d))?");
		Matcher m = timePattern.matcher(parameter);
		if (m.matches()) {
			int hours = Integer.parseInt(m.group(1));
			int minutes = Integer.parseInt(m.group(2));
			int seconds = 0;

			if (m.group(4) != null) {
				seconds = Integer.parseInt(m.group(4));
			}
			if (0 <= hours && hours < 24 && 0 <= minutes && minutes < 60
					&& 0 <= seconds && seconds < 60) {
				return null;
			}
		}
		return TIME_ERROR;
	}
}
