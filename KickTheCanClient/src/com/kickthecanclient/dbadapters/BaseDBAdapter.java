package com.kickthecanclient.dbadapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kickthecanclient.constants.CommonConst;

/**
 * SQL実行処理関連の基底クラス.
 */
public class BaseDBAdapter<T, C extends BaseColumn> {

	protected C[] columns;
	protected String tableName;
	protected Class<T> clazz;
	protected Context context;
	protected SQLiteDatabase db;
	protected DBOpenHelper<C> dbHelper;

	protected BaseDBAdapter(Context context, Class<T> clazz, String tableName, C[] columns) {
		this.tableName = tableName;
		this.clazz = clazz;
		this.columns = columns;
		this.dbHelper = new DBOpenHelper<>(context, this.tableName, this.columns);
	}

	public void open() {
		this.db = this.dbHelper.getWritableDatabase();
	}

	public void close(){
		this.dbHelper.close();
	}

	protected void insert(T content) {
		this.db.insert(tableName, null, toContentValues(content));
	}

	protected void update(WhereBuilder builder, T content) {
		this.db.update(tableName, toContentValues(content), builder.getConditions(), null);
	}

	protected T selectById(WhereBuilder builder){
		Cursor cursor = this.db.query(tableName, null, builder.getConditions(), builder.getValueArray(), null, null, null);
		return toEntities(cursor).get(0);
	}

	protected List<T> selectBy(WhereBuilder builder, String orderBy){
		Cursor cursor = this.db.query(tableName, null, builder.getConditions(), builder.getValueArray(), null, null, orderBy);
		return toEntities(cursor);
	}

	protected List<T> selectAll(String orderBy){
		Cursor cursor = this.db.query(tableName, null, null, null, null, null, orderBy);
		return toEntities(cursor);
	}

	protected boolean deleteBy(WhereBuilder builder){
		return this.db.delete(tableName, builder.getConditions(), null) > 0;
	}

	protected boolean deleteAll(){
		return this.db.delete(tableName, null, null) > 0;
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
			Map<String, Object> map = mapper.readValue(mapper.writeValueAsString(o), Map.class);
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				contentValues.put(entry.getKey(), entry.getValue() == null ? CommonConst.EMPTY : entry.getValue().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contentValues;
	}
}
