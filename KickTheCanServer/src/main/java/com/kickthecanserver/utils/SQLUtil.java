package com.kickthecanserver.utils;

import com.kickthecanserver.constants.CommonConst;
import com.kickthecanserver.constants.SQLConst;

/**
 * SQL処理クラス.
 *
 * @author ebihara
 */
public class SQLUtil {

	public static String inSingleQuote(String target) {
		return StringUtil.join(CommonConst.SINGLE_QUOTE, target, CommonConst.SINGLE_QUOTE);
	}

	public static String inParentheses(String target) {
		return StringUtil.join(SQLConst.START_PARENTHESES, target, SQLConst.END_PARENTHESES);
	}
}
