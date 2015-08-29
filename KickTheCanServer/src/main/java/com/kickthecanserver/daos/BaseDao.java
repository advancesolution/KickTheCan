package com.kickthecanserver.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Table;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.kickthecanserver.constants.CommonConst;
import com.kickthecanserver.utils.CaseUtil;
import com.kickthecanserver.utils.StringUtil;

/**
 * Dao基底クラス.
 *
 * @author ebihara
 */
@Component
public abstract class BaseDao<T> {

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	private Class<T> clazz = null;
	private String tableName = null;

	protected BaseDao(Class<T> clazz) {
		this.clazz = clazz;
		this.tableName = clazz.getAnnotation(Table.class).name();
	}

	protected List<T> selectByList(String condition) {
		return toBeans(jdbcTemplate.queryForList(getQuery(condition)));
	}

	protected T selectBySingleResult(String condition) {
		return toBean(jdbcTemplate.queryForMap(getQuery(condition)));
	}

	protected List<T> selectAll() {
		return toBeans(jdbcTemplate.queryForList(getQuery(null)));
	}

	private String getQuery(String condition) {
		String where = condition == null ? CommonConst.EMPTY : StringUtil.joinSeparator(CommonConst.HALF_SPACE, new String[] {"where", condition});
		return StringUtil.joinSeparator(CommonConst.HALF_SPACE, new String[] {"select * from", tableName, where});
	}

	private List<T> toBeans(List<Map<String, Object>> results) {
		List<T> entities = new ArrayList<>();
		results.forEach(m -> entities.add(toBean(m)));
		return entities;
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
