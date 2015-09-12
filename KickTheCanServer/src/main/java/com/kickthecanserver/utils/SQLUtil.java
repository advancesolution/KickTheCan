package com.kickthecanserver.utils;

import java.util.Objects;

import com.kickthecanserver.enums.HalfSymbol;

/**
 * SQL処理クラス.
 *
 * @author ebihara
 */
public class SQLUtil {

	public static String inSingleQuote(Object target) {
		return StringUtil.join(HalfSymbol.SINGLE_QUOTE.getValue(), Objects.isNull(target) ?
				null : target.toString(), HalfSymbol.SINGLE_QUOTE.getValue());
	}

	public static String inParentheses(String target) {
		return StringUtil.join(HalfSymbol.START_PARENTHESES.getValue(),
				target, HalfSymbol.END_PARENTHESES.getValue());
	}
}
