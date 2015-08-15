package com.kickthecanclient.dbadapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kickthecanclient.dbadapters.BaseDBAdapter.BaseColumn;
import com.kickthecanclient.utils.StringUtil;

/**
 * SQL実行処理関連の基底クラス.
 */
public class BaseDBAdapter<T, C extends BaseColumn> {

	protected C[] columns;
	protected String tableName;
	protected Class<T> clazz;
	protected Context context;
	protected SQLiteDatabase db;

	public interface BaseColumn {
		String getName();
		String getType();
		boolean isPrimaryKey();
	}

	protected BaseDBAdapter(Class<T> clazz, String tableName, C[] columns) {
		this.tableName = tableName;
		this.clazz = clazz;
		this.columns = columns;
	}

	protected void insert(T content) {
		this.db.insert(tableName, null, toContentValues(content));
	}

	protected void update(Map<String, String> params, T content) {
		this.db.update(tableName, toContentValues(content), makeWhereSetParams(params), null);
	}

	protected T selectById(Map<String, String> params){
		Cursor cursor = this.db.query(tableName, null, makeWhere(params), getValueArray(params), null, null, null);
		return toEntities(cursor).get(0);
	}

	protected List<T> selectBy(Map<String, String> params){
		Cursor cursor = this.db.query(tableName, null, makeWhere(params), getValueArray(params), null, null, null);
		return toEntities(cursor);
	}

	protected List<T> selectAll(){
		Cursor cursor = this.db.query(tableName, null, null, null, null, null, null);
		return toEntities(cursor);
	}

	protected boolean deleteBy(Map<String, String> params){
		return this.db.delete(tableName, makeWhereSetParams(params), null) > 0;
	}

	protected boolean deleteAll(){
		return this.db.delete(tableName, null, null) > 0;
	}

	protected String[] getValueArray(Map<String, String> params) {
		String[] valueArray = new String[params.size()];
		int index = 0;
		for (Entry<String, String> param : params.entrySet()) {
			valueArray[index++] = param.getValue();
		}
		return valueArray;
	}

	protected String makeWhereSetParams(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> param : params.entrySet()) {
			if (sb.length() != 0) {
				sb.append(" AND ");
			}
			sb.append(StringUtil.join(param.getKey(), "= '", param.getValue(), "'"));
		}
		return sb.toString();
	}

	protected String makeWhere(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> param : params.entrySet()) {
			if (sb.length() != 0) {
				sb.append(" AND ");
			}
			sb.append(StringUtil.join(param.getKey(), "= ?"));
		}
		return sb.toString();
	}

	protected List<T> toEntities(Cursor cursor) {
		List<T> entities = new ArrayList<>();
		if(cursor.moveToFirst()){
			do {
				entities.add(toEntity(cursor));
			} while(cursor.moveToNext());
		}
		return entities;
	}

	protected T toEntity(Cursor cursor){
		T entity = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> map = new HashMap<>();
			for (C column : this.columns) {
				map.put(column.getName(), cursor.getString(cursor.getColumnIndex(column.getName())));
			}
			entity = mapper.readValue(mapper.writeValueAsString(map), clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	protected ContentValues toContentValues(Object o) {
		ContentValues contentValues = new ContentValues();
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> map = mapper.readValue(mapper.writeValueAsString(o), Map.class);
			for (Map.Entry<String, String> entry : map.entrySet()) {
				contentValues.put(entry.getKey(), entry.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contentValues;
	}
}
