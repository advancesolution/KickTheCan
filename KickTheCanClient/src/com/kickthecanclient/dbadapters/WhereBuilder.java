package com.kickthecanclient.dbadapters;

import java.util.ArrayList;
import java.util.List;

import com.kickthecanclient.enums.HalfSymbol;
import com.kickthecanclient.enums.OperatorSymbol;
import com.kickthecanclient.utils.SQLUtil;
import com.kickthecanclient.utils.StringUtil;

/**
 * Where句生成用クラス.
 *
 * @author ebihara
 */
public class WhereBuilder {

	private String condition;
	private String needValueCondition;
	private List<String> valueList;

	public WhereBuilder() {
		this.condition = StringUtil.EMPTY;
		this.needValueCondition = StringUtil.EMPTY;
		this.valueList = new ArrayList<>();
	}

	public String getCondition() {
		return SQLUtil.inParentheses(this.condition);
	}

	public String getNeedValueCondition() {
		this.needValueCondition = this.condition;
		for (String value : this.valueList) {
			this.needValueCondition = this.needValueCondition.replaceAll(
					SQLUtil.inSingleQuote(value), HalfSymbol.QUESTION.getValue());
		}
		return SQLUtil.inParentheses(this.needValueCondition);
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String[] getValueArray() {
		return (String[]) valueList.toArray(new String[0]);
	}

	public WhereBuilder and() {
		this.condition = StringUtil.join(this.condition, HalfSymbol.SPACE.getValue(),  OperatorSymbol.AND.getValue());
		return this;
	}

	public WhereBuilder or() {
		this.condition = StringUtil.join(this.condition, HalfSymbol.SPACE.getValue(), OperatorSymbol.OR.getValue());
		return this;
	}

	public WhereBuilder eq(String itemName, String value) {
		joinItem(itemName, getQueryValue(value), OperatorSymbol.EQUAL.getValue());
		return this;
	}

	public WhereBuilder le(String itemName, String value) {
		joinItem(itemName, getQueryValue(value), OperatorSymbol.LEFT_EQUAL.getValue());
		return this;
	}

	public WhereBuilder re(String itemName, String value) {
		joinItem(itemName, getQueryValue(value), OperatorSymbol.RIGHT_EQUAL.getValue());
		return this;
	}

	public WhereBuilder la(String itemName, String value) {
		joinItem(itemName, getQueryValue(value), OperatorSymbol.LARGE.getValue());
		return this;
	}

	public WhereBuilder sm(String itemName, String value) {
		joinItem(itemName, getQueryValue(value), OperatorSymbol.SMALL.getValue());
		return this;
	}

	public WhereBuilder ne(String itemName, String value) {
		joinItem(itemName, getQueryValue(value), OperatorSymbol.NOT_EQUAL.getValue());
		return this;
	}

	public WhereBuilder in(String itemName, String[] values) {
		joinItem(itemName, getInQuery(values), OperatorSymbol.IN.getValue());
		return this;
	}

	public WhereBuilder notIn(String itemName, String[] values) {
		joinItem(itemName, getInQuery(values), OperatorSymbol.NOT_IN.getValue());
		return this;
	}

	private String getQueryValue(String value) {
		valueList.add(value);
		return SQLUtil.inSingleQuote(value);
	}

	private void joinItem(String itemName, String value, String condition) {
		this.condition = StringUtil.joinSeparator(HalfSymbol.SPACE.getValue(),
				new String[]{StringUtil.EMPTY, this.condition, itemName, condition, value});
	}

	private String getInQuery(String[] values) {

		String[] array = new String[values.length];

		int index = 0;
		for (String value : values) {
			array[index++] = getQueryValue(value);
		}

		return SQLUtil.inParentheses(StringUtil.joinSeparator(HalfSymbol.COMMA.getValue(), array));
	}
}
