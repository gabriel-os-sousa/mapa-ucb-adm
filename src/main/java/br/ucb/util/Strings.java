package br.ucb.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Strings {
	
	private static final DateFormat DATA_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	private static final DateFormat DATA_HORA_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  

	public static boolean isEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}

	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}
	
	public static boolean isNotNull(String value) {
		return !isEmpty(value) && !"null".equalsIgnoreCase(value.trim());
	}
	
	public static String dateToString(Date date) {
		return DATA_FORMAT.format(date);
	}
	
	public static String dateToString(Long timeMillis) {
		return dateToString(new Date(timeMillis)); 
	}
	
	public static String dateHourToString(Date date) {
		return DATA_HORA_FORMAT.format(date);
	}
	
	public static String dateHourToString(Long timeMillis) {
		return dateHourToString(new Date(timeMillis)); 
	}
	

}
