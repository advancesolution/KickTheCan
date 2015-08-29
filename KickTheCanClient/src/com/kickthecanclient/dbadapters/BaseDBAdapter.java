package com.kickthecanclient.dbadapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kickthecanclient.beans.property.PropertyBean;
import com.kickthecanclient.utils.PropertyUtil;

/**
 * SQL実行処理関連の基底クラス.
 *
 * @author ebihara
 */
public class BaseDBAdapter<T, C extends BaseColumn> {

	protected String tableName;
	protected Class<T> clazz;
	protected Context context;
	protected SQLiteDatabase db;
	protected DBOpenHelper<C> dbHelper;

	protected BaseDBAdapter(Context context, Class<T> clazz, C[] columns) {
		this.tableName = clazz.getAnnotation(Table.class).name();
		this.clazz = clazz;
		this.dbHelper = new DBOpenHelper<>(context, this.tableName, columns);
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
		this.db.update(tableName, toContentValues(content), builder.getCondition(), null);
	}

	protected T selectById(WhereBuilder builder){
		Cursor cursor = this.db.query(tableName, null, builder.getNeedValueCondition(), builder.getValueArray(), null, null, null);
		List<T> entities = toEntities(cursor);
		return entities.isEmpty() ? null : entities.get(0);
	}

	protected List<T> selectBy(WhereBuilder builder, String orderBy){
		Cursor cursor = this.db.query(tableName, null, builder.getNeedValueCondition(), builder.getValueArray(), null, null, orderBy);
		return toEntities(cursor);
	}

	protected List<T> selectAll(String orderBy){
		Cursor cursor = this.db.query(tableName, null, null, null, null, null, orderBy);
		return toEntities(cursor);
	}

	protected boolean deleteBy(WhereBuilder builder){
		return this.db.delete(tableName, builder.getCondition(), null) > 0;
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

	protected T toEntity(Cursor cursor) {
		T entity = null;
		try {
			entity = this.clazz.newInstance();
			for (String columnName : cursor.getColumnNames()) {
				String value =cursor.getString(cursor.getColumnIndex(columnName));
				PropertyUtil.setProperty(
						entity, columnName, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

	protected ContentValues toContentValues(Object o) {
		ContentValues contentValues = new ContentValues();
		List<PropertyBean> propertyBeans = PropertyUtil.getPropertyBeans(this.clazz);
		for (PropertyBean propertyBean : propertyBeans) {
			contentValues.put(propertyBean.getName(), Objects.toString(
					PropertyUtil.getProperty(o, propertyBean.getName()), null));
		}
		return contentValues;
	}
}
