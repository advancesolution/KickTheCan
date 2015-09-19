package com.kickthecanclient.dbadapters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kickthecanclient.enums.ColumnType;
import com.kickthecanclient.enums.HalfSymbol;
import com.kickthecanclient.utils.CaseUtil;
import com.kickthecanclient.utils.StringUtil;

/**
 * DB定義処理用クラス.
 *
 * @author ebihara
 */
public class DBOpenHelper<T> extends SQLiteOpenHelper {

	protected String tableName;

	private Class<T> clazz = null;

	private static final String DB = "kick_the_can.db";

	public DBOpenHelper(Context c, Class<T> clazz) {
		super(c, DB, null, 2);
		this.clazz = clazz;
		this.tableName = clazz.getAnnotation(Table.class).name();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();

		T entity = null;
		try {
			entity = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		for (Field field : entity.getClass().getDeclaredFields()) {
			if (sb.length() == 0) {
				sb.append(StringUtil.joinSeparator(HalfSymbol.SPACE.getValue(),
						new String[]{"CREATE TABLE", tableName, "("}));
			} else {
				sb.append(HalfSymbol.COMMA.getValue());
			}

			StringBuilder line = new StringBuilder();
			line.append(StringUtil.join(HalfSymbol.SPACE.getValue(), CaseUtil.camelToSnake(field.getName())));
			if (field.getType() == String.class) {
				line.append(StringUtil.join(HalfSymbol.SPACE.getValue(), ColumnType.TEXT.name()));
			} else if (field.getType() == Integer.TYPE || field.getType() == Integer.class) {
				line.append(StringUtil.join(HalfSymbol.SPACE.getValue(), ColumnType.NUMBER.name()));
			}
			for (Annotation a : field.getAnnotations()) {
				if (a instanceof Id) {
					line.append(StringUtil.join(HalfSymbol.SPACE.getValue(), "PRIMARY KEY"));
				} else if (a instanceof NotNull) {
					line.append(StringUtil.join(HalfSymbol.SPACE.getValue(), "NOT NULL"));
				}
			}
			sb.append(line.toString());
		}
		sb.append(");");

		db.execSQL(sb.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(StringUtil.join("DROP TABLE IF EXISTS ", tableName, ";"));
		onCreate(db);
	}

	@Override
	public void onConfigure(SQLiteDatabase db) {
		db.setVersion(1);
	}
}
