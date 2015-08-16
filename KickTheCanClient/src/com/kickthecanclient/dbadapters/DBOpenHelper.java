package com.kickthecanclient.dbadapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kickthecanclient.constants.CommonConst;
import com.kickthecanclient.utils.StringUtil;

/**
 * DB定義処理用クラス.
 */
public class DBOpenHelper<T extends BaseColumn> extends SQLiteOpenHelper {

	protected String tableName;
	protected T[] entities;

	private static final String DB = "kickTheCan.db";
	private static final int DB_VERSION = 2;

	public DBOpenHelper(Context c, String tableName, T[] entities) {
		super(c, DB, null, DB_VERSION);
		this.tableName = tableName;
		this.entities = entities;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		for (T entity : entities) {
			if (sb.length() == 0) {
				sb.append(StringUtil.joinSeparator(CommonConst.HALF_SPACE, new String[]{"CREATE TABLE", tableName, "("}));
			} else {
				sb.append(CommonConst.COMMA);
			}
			StringBuilder line = new StringBuilder();
			line.append(StringUtil.joinSeparator(CommonConst.HALF_SPACE, new String[]{
					entity.getName(), entity.getType().name(),
					entity.isPrimaryKey() ? "PRIMARY KEY" : CommonConst.EMPTY,
					entity.isNotNull() ? "NOT NULL" : CommonConst.EMPTY}));
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
}
