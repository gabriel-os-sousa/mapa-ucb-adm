package br.ucb.util;

public class Strings {

	public static boolean isEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}

	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}
	
	public static boolean isNotNull(String value) {
		return !isEmpty(value) && !"null".equalsIgnoreCase(value.trim());
	}

}
