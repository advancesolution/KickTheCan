package com.kickthecanclient.utils;

import com.kickthecanclient.enums.HalfSymbol;

/**
 * SQL処理クラス.
 *
 * @author ebihara
 */
public class SQLUtil {

	public static String inSingleQuote(String target) {
		return StringUtil.join(HalfSymbol.SINGLE_QUOTE.getValue(),
				target, HalfSymbol.SINGLE_QUOTE.getValue());
	}

	public static String inParentheses(String target) {
		return StringUtil.join(HalfSymbol.START_PARENTHESES.getValue(),
				target, HalfSymbol.END_PARENTHESES.getValue());
	}
}
