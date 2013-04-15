package org.wintrisstech.erik.sprinkler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Schedule {
	private static final Logger logger = Logger
			.getLogger(RegistrationServlet.class.getName());
	private static final TimeZone TZ = TimeZone.getTimeZone("PST");

	private boolean[] par00DOW;
	private boolean par00OnOff;
	private Date par00TimeOn;
	private Date par00TimeOff;
	private boolean[] par00Zones;

	// private boolean par01Dow0; // Sunday
	// private boolean par01Dow1;
	// private boolean par01Dow2;
	// private boolean par01Dow3;
	// private boolean par01Dow4;
	// private boolean par01Dow5;
	// private boolean par01Dow6; // Saturday
	// private boolean par01OnOff;
	// private Date par01TimeOn;
	// private Date par01TimeOff;
	// private boolean par01Zone0;
	// private boolean par01Zone1;
	// private boolean par01Zone2;
	// private boolean par01Zone3;
	// private boolean par01Zone4;
	// private boolean par01Zone5;
	//
	// private boolean par02Dow0; // Sunday
	// private boolean par02Dow1;
	// private boolean par02Dow2;
	// private boolean par02Dow3;
	// private boolean par02Dow4;
	// private boolean par02Dow5;
	// private boolean par02Dow6; // Saturday
	// private boolean par02OnOff;
	// private Date par02TimeOn;
	// private Date par02TimeOff;
	// private boolean par02Zone0;
	// private boolean par02Zone1;
	// private boolean par02Zone2;
	// private boolean par02Zone3;
	// private boolean par02Zone4;
	// private boolean par02Zone5;

	Schedule() {

	}

	public void setTime(String timeOn, String timeOff) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		dateFormat.setTimeZone(TZ);
		try {
			par00TimeOn = dateFormat.parse(timeOn);
			par00TimeOff = dateFormat.parse(timeOff);
		} catch (ParseException e) {
			e.printStackTrace();
			logger.log(Level.SEVERE,
					"Time is entered incorrectly. TimeOn = {0}. TimeOff = {1}",
					new Object[] { timeOn, timeOff });
		}
	}

	public String getTimeOn() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		dateFormat.setTimeZone(TZ);
		return dateFormat.format(par00TimeOn);
	}

	public String getTimeOff() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		dateFormat.setTimeZone(TZ);
		return dateFormat.format(par00TimeOff);
	}

	public void setOnOff(boolean scheduleOn) {
		par00OnOff = scheduleOn;
	}
	
	public boolean getOnOff(){
		return par00OnOff;
	}

	public void setZones(boolean[] zones) {
		par00Zones = zones;
	}
	
	public boolean[] getZones(){
		return par00Zones.clone();
	}

	public void setDOW(boolean[] DOW) {
		par00DOW = DOW;
	}
	
	public boolean[] getDOW(){
		return par00DOW.clone();
	}

}
