package com.kickthecanserver.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表記変換処理クラス.
 */
public class CaseUtil {

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
}
