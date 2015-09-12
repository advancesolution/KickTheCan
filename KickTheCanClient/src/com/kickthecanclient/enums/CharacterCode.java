package com.kickthecanclient.enums;

/**
 * 文字コード.
 *
 * @author ebihara
 */
public enum CharacterCode {
	UTF_8 ("utf-8"),
	;

	private String value;

	CharacterCode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
