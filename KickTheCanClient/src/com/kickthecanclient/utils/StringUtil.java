package com.kickthecanclient.utils;

/**
 * 文字列処理クラス.
 *
 * @author ebihara
 */
public class StringUtil {

	public static final String EMPTY = "";

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isEmpty(String str) {
		return str == null || EMPTY.equals(str);
	}

	public static String join(String... strArray) {
		StringBuilder sb = new StringBuilder();
		for (String str : strArray) {
			sb.append(str);
		}
		return sb.toString();
	}

	public static String joinSeparator(String separator, String[] strArray) {
		StringBuilder sb = new StringBuilder();
		for (String str : strArray) {
			if (sb.length() != 0) {
				sb.append(separator);
			}
			sb.append(str);
		}
		return sb.toString();
	}
}
