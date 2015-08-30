package com.kickthecanserver.utils;

import java.util.Objects;

import com.kickthecanserver.constants.CommonConst;
import com.kickthecanserver.constants.SQLConst;

/**
 * SQL処理クラス.
 *
 * @author ebihara
 */
public class SQLUtil {

	public static String inSingleQuote(Object target) {
		return StringUtil.join(CommonConst.SINGLE_QUOTE, Objects.isNull(target) ? null : target.toString(), CommonConst.SINGLE_QUOTE);
	}

	public static String inParentheses(String target) {
		return StringUtil.join(SQLConst.START_PARENTHESES, target, SQLConst.END_PARENTHESES);
	}
}
