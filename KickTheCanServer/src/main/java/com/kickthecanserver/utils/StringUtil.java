package com.kickthecanserver.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 文字列処理クラス.
 */
public class StringUtil {

	public static String join(String... array) {
		return Arrays.asList(array).stream().collect(Collectors.joining());
	}
}
