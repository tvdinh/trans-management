package com.tdinh.interview.codeme.trans.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
	
	public static Date fromString(String dateString) throws ParseException {
		return dateTimeFormat.parse(dateString);
	}
}
