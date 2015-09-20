package com.kickthecanclient.dbadapters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Id;
import javax.persistence.Table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kickthecanclient.beans.PropertyBean;
import com.kickthecanclient.utils.CaseUtil;
import com.kickthecanclient.utils.PropertyUtil;

/**
 * SQL実行処理関連の基底クラス.
 *
 * @author ebihara
 */
public class BaseDBAdapter<T> {

	private String tableName;
	private Class<T> clazz;
	private SQLiteDatabase db;
	private DBOpenHelper<T> dbHelper;

	protected BaseDBAdapter(Context context, Class<T> clazz) {
		this.tableName = clazz.getAnnotation(Table.class).name();
		this.clazz = clazz;
		this.dbHelper = new DBOpenHelper<>(context, clazz);
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

	protected void update(T content) {
		this.db.update(tableName, toContentValues(content), getPrimaryWhereQuery(content), null);
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

	private List<T> toEntities(Cursor cursor) {
		List<T> entities = new ArrayList<>();
		if(cursor.moveToFirst()){
			do {
				entities.add(toEntity(cursor));
			} while(cursor.moveToNext());
		}
		return entities;
	}

	private T toEntity(Cursor cursor) {
		T entity = null;
		try {
			entity = this.clazz.newInstance();
			for (String columnName : cursor.getColumnNames()) {
				String itemName = CaseUtil.toLowerCamel(columnName);
				Field field = this.clazz.getDeclaredField(itemName);
				if (field.getType() == String.class) {
					PropertyUtil.setProperty(
							entity, itemName, cursor.getString(cursor.getColumnIndex(columnName)));
				} else if (field.getType() == Integer.TYPE || field.getType() == Integer.class) {
					PropertyUtil.setProperty(
							entity, itemName, cursor.getInt(cursor.getColumnIndex(columnName)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

	private ContentValues toContentValues(Object o) {
		ContentValues contentValues = new ContentValues();

		List<PropertyBean> propertyBeans = PropertyUtil.getPropertyBeans(this.clazz);

		for (PropertyBean propertyBean : propertyBeans) {
			try {
				contentValues.put(CaseUtil.camelToSnake(propertyBean.getName()),
						Objects.toString(PropertyUtil.getProperty(o, propertyBean.getName()), null));
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return contentValues;
	}

	private String getPrimaryWhereQuery(T content) {
		WhereBuilder builder = new WhereBuilder();
		T entity = null;
		try {
			entity = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Field field : entity.getClass().getDeclaredFields()) {
			for (Annotation a : field.getAnnotations()) {
				if (a instanceof Id) {
					if (builder.isNotEmpty()) {
						builder.and();
					}
					try {
						builder.eq(CaseUtil.camelToSnake(
								field.getName()),PropertyUtil.getProperty(
								content, field.getName()).toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return builder.getCondition();
	}
}
