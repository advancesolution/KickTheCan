package com.kickthecanserver.daos;

import com.kickthecanserver.constants.CommonConst;
import com.kickthecanserver.constants.SQLConst;
import com.kickthecanserver.utils.SQLUtil;
import com.kickthecanserver.utils.StringUtil;

/**
 * Where句生成用クラス.
 *
 * @author ebihara
 */
public class WhereBuilder {

	private String condition;

	public WhereBuilder() {
		this.condition = CommonConst.EMPTY;
	}

	public String getCondition() {
		return SQLUtil.inParentheses(this.condition);
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public WhereBuilder and() {
		this.condition = StringUtil.join(this.condition, CommonConst.HALF_SPACE,  SQLConst.AND);
		return this;
	}

	public WhereBuilder or() {
		this.condition = StringUtil.join(this.condition, CommonConst.HALF_SPACE, SQLConst.OR);
		return this;
	}

	public WhereBuilder eq(String itemName, String value) {
		joinItem(itemName, getQueryValue(value), SQLConst.EQUAL);
		return this;
	}

	public WhereBuilder le(String itemName, String value) {
		joinItem(itemName, getQueryValue(value), SQLConst.LEFT_EQUAL);
		return this;
	}

	public WhereBuilder re(String itemName, String value) {
		joinItem(itemName, getQueryValue(value), SQLConst.RIGHT_EQUAL);
		return this;
	}

	public WhereBuilder la(String itemName, String value) {
		joinItem(itemName, getQueryValue(value), SQLConst.LARGE);
		return this;
	}

	public WhereBuilder sm(String itemName, String value) {
		joinItem(itemName, getQueryValue(value), SQLConst.SMALL);
		return this;
	}

	public WhereBuilder ne(String itemName, String value) {
		joinItem(itemName, getQueryValue(value), SQLConst.NOT_EQUAL);
		return this;
	}

	public WhereBuilder in(String itemName, String[] values) {
		joinItem(itemName, getInQuery(values), SQLConst.IN);
		return this;
	}

	public WhereBuilder notIn(String itemName, String[] values) {
		joinItem(itemName, getInQuery(values), SQLConst.NOT_IN);
		return this;
	}

	private String getQueryValue(String value) {
		return SQLUtil.inSingleQuote(value);
	}

	private void joinItem(String itemName, String value, String condition) {
		this.condition = StringUtil.joinSeparator(CommonConst.HALF_SPACE,
				new String[]{CommonConst.EMPTY, this.condition, itemName, condition, value});
	}

	private String getInQuery(String[] values) {

		String[] array = new String[values.length];

		int index = 0;
		for (String value : values) {
			array[index++] = getQueryValue(value);
		}

		return SQLUtil.inParentheses(StringUtil.joinSeparator(CommonConst.COMMA, array));
	}
}
