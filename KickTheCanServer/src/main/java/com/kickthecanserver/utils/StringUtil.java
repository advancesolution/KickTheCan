package com.kickthecanserver.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.kickthecanserver.constants.CommonConst;

/**
 * 文字列処理クラス.
 */
public class StringUtil {

	public static String nullToEmpty(String str) {
		return str == null ? CommonConst.EMPTY : str;
	}

	public static String join(String... array) {
		return Arrays.asList(array).stream().collect(Collectors.joining());
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
