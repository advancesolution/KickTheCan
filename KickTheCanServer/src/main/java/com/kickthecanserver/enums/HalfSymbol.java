package com.kickthecanserver.enums;

/**
 * 半角記号.
 *
 * @author ebihara
 */
public enum HalfSymbol {
	PERIOD ("."),
	COMMA (","),
	SPACE (" "),
	SINGLE_QUOTE ("'"),
	START_PARENTHESES ("("),
	END_PARENTHESES (")"),
	QUESTION ("?"),
	;

	private String value;

	HalfSymbol(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
