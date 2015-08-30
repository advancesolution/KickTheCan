package com.kickthecanserver.daos;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Table;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.kickthecanserver.constants.CommonConst;
import com.kickthecanserver.constants.SQLConst;
import com.kickthecanserver.utils.CaseUtil;
import com.kickthecanserver.utils.SQLUtil;
import com.kickthecanserver.utils.StringUtil;

/**
 * Dao基底クラス.
 *
 * @author ebihara
 */
@Component
public abstract class BaseDao<T, C extends BaseColumn> {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Class<T> clazz = null;
	private String tableName = null;
	private String selectQuery = null;
	private String insertQuery = null;
	private String updatetQuery = null;
	private List<String> primaryKeys = null;

	protected BaseDao(Class<T> clazz, C[] columns) {
		this.clazz = clazz;
		this.tableName = clazz.getAnnotation(Table.class).name();
		this.selectQuery = getSelectQuery();
		this.insertQuery = getInsertQuery();
		this.updatetQuery = getUpdateQuery();
		this.primaryKeys = Arrays.asList(columns).stream().filter(o -> o.isPrimaryKey()).map(o ->o.getName()).collect(Collectors.toList());
	}

	protected List<T> selectByList(String condition) {
		return toBeans(jdbcTemplate.queryForList(concatCondition(selectQuery, condition)));
	}

	protected T selectBySingleResult(String condition) {
		return toBean(jdbcTemplate.queryForMap(concatCondition(selectQuery, condition)));
	}

	protected List<T> selectAll() {
		return toBeans(jdbcTemplate.queryForList(concatCondition(selectQuery, null)));
	}

	protected void insert(T entity) {
		jdbcTemplate.update(insertQuery, getValues(entity));
	}

	protected void update(T entity) {
		jdbcTemplate.update(concatCondition(updatetQuery, getUpdateCondition(entity)), getValues(entity));
	}

	private List<PropertyDescriptor> getProperties() {
		return Arrays.asList(PropertyUtils.getPropertyDescriptors(clazz)).
				stream().filter(o -> !"class".equals(o.getName())).collect(Collectors.toList());
	}

	private String getWhere(String condition) {
		return condition == null ? CommonConst.EMPTY : StringUtil.joinSeparator(
				CommonConst.HALF_SPACE, new String[] {SQLConst.WHERE, condition});
	}

	private String getSelectQuery() {
		return StringUtil.joinSeparator(CommonConst.HALF_SPACE, new String[] {"SELECT * FROM", tableName});
	}

	private String concatCondition(String query, String condition) {
		return StringUtil.joinSeparator(CommonConst.HALF_SPACE, new String[] {query, getWhere(condition)});
	}

	private List<T> toBeans(List<Map<String, Object>> results) {
		List<T> entities = new ArrayList<>();
		results.forEach(m -> entities.add(toBean(m)));
		return entities;
	}

	private Object[] getValues(T entity) {
		List<PropertyDescriptor> properties = getProperties();
		List<Object> values = new ArrayList<>();

		properties.stream().filter(o -> !"class".equals(o.getName())).forEach(o -> {
			try {
				values.add(PropertyUtils.getProperty(entity, o.getName()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return values.toArray();
	}

	private String getUpdateQuery() {
		StringBuilder nameSb = new StringBuilder();

		List<PropertyDescriptor> properties = getProperties();

		properties.forEach(o -> {
			if (nameSb.length() != 0) {
				nameSb.append(CommonConst.COMMA);
			}
			nameSb.append(StringUtil.joinSeparator(CommonConst.HALF_SPACE, new String[] {
				CaseUtil.camelToSnake(
				o.getName()),
				SQLConst.EQUAL,
				SQLConst.QUESTION
			}));
		});

		String updateQuery = StringUtil.joinSeparator(CommonConst.HALF_SPACE,new String[] {
			"UPDATE",
			tableName,
			"SET",
			nameSb.toString()
		});

		return updateQuery;
	}

	private String getUpdateCondition(T entity) {
		WhereBuilder where = new WhereBuilder();

		primaryKeys.forEach(primaryKey -> {
			try {
				if (where.isNotEmpty()) {
					where.and();
				}
				where.eq(primaryKey, PropertyUtils.getProperty(entity, CaseUtil.toLowerCamel(primaryKey)).toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return where.getCondition();
	}

	private String getInsertQuery() {

		StringBuilder nameSb = new StringBuilder();
		StringBuilder valueSb = new StringBuilder();

		List<PropertyDescriptor> properties = getProperties();

		properties.forEach(o -> {
			if (nameSb.length() != 0) {
				nameSb.append(CommonConst.COMMA);
				valueSb.append(CommonConst.COMMA);
			}
			nameSb.append(CaseUtil.camelToSnake(o.getName()));
			valueSb.append(SQLConst.QUESTION);
		});

		String insertQuery = StringUtil.joinSeparator(CommonConst.HALF_SPACE, new String[]{
			"INSERT INTO",
			this.tableName,
			SQLUtil.inParentheses(nameSb.toString()),
			"VALUES",
			SQLUtil.inParentheses(valueSb.toString())
		});

		return insertQuery;
	}

	private T toBean(Map<String, Object> result) {
		T entity = null;
		try {
			entity = clazz.newInstance();

			for (Map.Entry<String, Object> entry : result.entrySet()) {
				String name = CaseUtil.snakeToCamel(entry.getKey());
				Object property = PropertyUtils.getProperty(entity, name);
				String value = entry.getValue().toString();
				if (property instanceof Integer) {
					PropertyUtils.setSimpleProperty(entity, name, Integer.parseInt(value));
				} else {
					PropertyUtils.setSimpleProperty(entity, name, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
}
