package com.kickthecanserver.daos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.kickthecanserver.enums.HalfSymbol;
import com.kickthecanserver.enums.OperatorSymbol;
import com.kickthecanserver.utils.SQLUtil;
import com.kickthecanserver.utils.StringUtil;

/**
 * Where句生成用クラス.
 *
 * @author ebihara
 */
public class WhereBuilder {

	private String condition;
	private List<Object> valueList;

	public WhereBuilder() {
		this.condition = StringUtil.EMPTY;
		this.valueList = new ArrayList<>();
	}

	public boolean isEmpty() {
		return StringUtils.isEmpty(this.condition);
	}

	public boolean isNotEmpty() {
		return !isEmpty();
	}

	public String getCondition() {
		return SQLUtil.inParentheses(this.condition);
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Object[] getValueArray() {
		return (Object[]) valueList.toArray(new Object[0]);
	}

	public WhereBuilder and() {
		this.condition = StringUtil.join(this.condition, HalfSymbol.SPACE.getValue(),  OperatorSymbol.AND.getValue());
		return this;
	}

	public WhereBuilder or() {
		this.condition = StringUtil.join(this.condition, HalfSymbol.SPACE.getValue(), OperatorSymbol.OR.getValue());
		return this;
	}

	public WhereBuilder eq(String itemName, Object value) {
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

	private String getQueryValue(Object value) {
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
