package com.kickthecanclient.enums;

/**
 * SQL演算子.
 *
 * @author ebihara
 */
public enum OperatorSymbol {
	EQUAL ("="),
	LEFT_EQUAL (">="),
	RIGHT_EQUAL ("<="),
	LARGE (">"),
	SMALL ("<"),
	NOT_EQUAL ("<>"),
	AND ("AND"),
	OR ("OR"),
	IN ("IN"),
	NOT_IN ("NOT IN"),
	;

	private String value;

	OperatorSymbol(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
