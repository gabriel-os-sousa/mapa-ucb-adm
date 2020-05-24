package br.ucb.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Strings {
	
	private static final DateFormat DATA_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	private static final DateFormat DATA_HORA_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	private static final DateFormat DATA_FORMAT_MILIS = new SimpleDateFormat("yyyy-MM-dd"); 
	private static final DateFormat DATA_HORA_FORMAT_MILIS = new SimpleDateFormat("yyyy-MM-dd hh:mm"); 

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
	
	public static String millisToString(Long timeMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeMillis);
		return DATA_FORMAT_MILIS.format(calendar.getTime()); 
	}
	
	public static Long stringToMillis (String data, String hora) {
		Long dateMillis = null;

		try {
            Date dt = DATA_HORA_FORMAT_MILIS.parse(data + " " + hora);
            Calendar ca = Calendar.getInstance();
            ca.setTime(dt);
            dateMillis = ca.getTimeInMillis();
            System.out.println("stringToMilis: "+ca.getTimeInMillis());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return dateMillis;
	}
	
	
	
	
	
}
