package com.kickthecanclient.dbadapters;

import java.util.ArrayList;
import java.util.List;

import com.kickthecanclient.constants.CommonConst;
import com.kickthecanclient.constants.SQLConst;
import com.kickthecanclient.utils.SQLUtil;
import com.kickthecanclient.utils.StringUtil;

/**
 * Where句生成用クラス.
 */
public class WhereBuilder {

	private String conditions;
	private List<String> valueList;
	private boolean separateParam;

	/**
	 * コンストラクタ
	 *
	 * @param separateParam false:パラメータを引数に分割しない true:パラメータを引数に分割する
	 */
	public WhereBuilder(boolean separateParam) {
		this.conditions = CommonConst.EMPTY;
		this.valueList = new ArrayList<>();
		this.separateParam = separateParam;
	}

	public String getConditions() {
		return SQLUtil.inParentheses(conditions);
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String[] getValueArray() {
		return (String[]) valueList.toArray(new String[0]);
	}

	public WhereBuilder and() {
		conditions = StringUtil.join(conditions, CommonConst.HALF_SPACE,  SQLConst.AND);
		return this;
	}

	public WhereBuilder or() {
		conditions = StringUtil.join(conditions, CommonConst.HALF_SPACE, SQLConst.OR);
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
		valueList.add(value);
		return this.separateParam ? SQLConst.QUESTION : SQLUtil.inSingleQuote(value);
	}

	private void joinItem(String itemName, String value, String condition) {
		conditions = StringUtil.joinSeparator(CommonConst.HALF_SPACE,
	 		new String[]{CommonConst.EMPTY, conditions, itemName, condition, value});
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
