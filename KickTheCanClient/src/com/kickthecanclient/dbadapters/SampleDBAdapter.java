package com.kickthecanclient.dbadapters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.kickthecanclient.dbadapters.BaseDBAdapter.BaseColumn;
import com.kickthecanclient.dbadapters.SampleDBAdapter.SampleColumn;
import com.kickthecanclient.entities.Sample;

/**
 * お試しSQLite版Daoクラス.
 */
public class SampleDBAdapter extends BaseDBAdapter<Sample, SampleColumn> {

	private static final String TABLE_NAME = "Sample";

	public enum SampleColumn implements BaseColumn {
		USER_ID {
			@Override
			public String getName() {
				return "userId";
			}
			@Override
			public String getType() {
				return "TEXT";
			}
			@Override
			public boolean isPrimaryKey() {
				return true;
			}
		},

		PASSWORD {
			@Override
			public String getName() {
				return "password";
			}
			@Override
			public String getType() {
				return "TEXT";
			}
			@Override
			public boolean isPrimaryKey() {
				return false;
			}
		},

		USER_NAME {
			@Override
			public String getName() {
				return "userName";
			}
			@Override
			public String getType() {
				return "TEXT";
			}
			@Override
			public boolean isPrimaryKey() {
				return false;
			}
		};
	};

	private DBOpenHelper<SampleColumn> dbHelper;

	public SampleDBAdapter(Context context){
		super(Sample.class, TABLE_NAME, SampleColumn.values());
		this.dbHelper = new DBOpenHelper<>(context, TABLE_NAME, SampleColumn.values());
	}

	public void open() {
		this.db = this.dbHelper.getWritableDatabase();
	}

	public void close(){
		this.dbHelper.close();
	}

	public void insert(Sample sample) {
		super.insert(sample);
	}

	public void update(Sample sample) {
		Map<String, String> params = new HashMap<>();
		params.put(SampleColumn.USER_ID.getName(), sample.getUserId());
		super.update(params, sample);
	}

	public Sample selectById(String userId){
		Map<String, String> params = new HashMap<>();
		params.put(SampleColumn.USER_ID.getName(), userId);
		return super.selectById(params);
	}

	public List<Sample> selectByUserName(String userName){
		Map<String, String> params = new HashMap<>();
		params.put(SampleColumn.USER_NAME.getName(), userName);
		return super.selectBy(params);
	}

	public List<Sample> selectAll(){
		return super.selectAll();
	}

	public boolean deleteByUserId(String userId){
		Map<String, String> params = new HashMap<>();
		params.put(SampleColumn.USER_ID.getName(), userId);
		return super.deleteBy(params);
	}

	public boolean deleteAll(){
		return super.deleteAll();
	}
}
