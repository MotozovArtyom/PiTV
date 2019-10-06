package ru.miem.hse.pitv.common;

public final class Strings {
	private static final String EMPTY_STRING = "";

	private Strings() {
	}

	public static String empty() {
		return EMPTY_STRING;
	}

	public static String nullToEmpty(String string) {
		return string == null ? Strings.EMPTY_STRING : string;
	}

	public static String emptyToNull(String string) {
		return isNullOrEmpty(string) ? null : string;
	}

	public static boolean isNullOrEmpty(String string) {
		return string == null || string.isEmpty();
	}
}
