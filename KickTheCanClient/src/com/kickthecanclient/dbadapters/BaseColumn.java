package com.kickthecanclient.dbadapters;

import com.kickthecanclient.enums.ColumnType;

/**
 * SQL実行処理関連の基底クラス.
 *
 * @author ebihara
 */
public interface BaseColumn {
	String getName();
	ColumnType getType();
	boolean isPrimaryKey();
	boolean isNotNull();
}
