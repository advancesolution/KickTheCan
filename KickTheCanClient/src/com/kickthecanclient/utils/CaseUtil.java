package com.kickthecanclient.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表記変換処理クラス.
 *
 * @author ebihara
 */
public class CaseUtil {

	public static String toLowerCamel(String target) {
		return snakeToCamel(camelToSnake(target));
	}

	public static String snakeToCamel(String target) {
		Pattern p = Pattern.compile("_([a-z])");
		Matcher m = p.matcher(target.toLowerCase());
		StringBuffer sb = new StringBuffer(target.length());
		while (m.find()) {
			m.appendReplacement(sb, m.group(1).toUpperCase());
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public static String camelToSnake(String target) {
		String result = target
				.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
				.replaceAll("([a-z])([A-Z])", "$1_$2");
		return result.toLowerCase();
	}
}
