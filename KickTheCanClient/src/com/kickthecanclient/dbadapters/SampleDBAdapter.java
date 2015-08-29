package com.kickthecanclient.dbadapters;

import java.util.List;

import android.content.Context;

import com.kickthecanclient.dbadapters.SampleDBAdapter.SampleColumn;
import com.kickthecanclient.entities.Sample;
import com.kickthecanclient.enums.ColumnType;

/**
 * お試しSQLite版Daoクラス.
 *
 * @author ebihara
 */
public class SampleDBAdapter extends BaseDBAdapter<Sample, SampleColumn> {

	public enum SampleColumn implements BaseColumn {
		ID {
			@Override
			public String getName() {
				return "id";
			}
			@Override
			public ColumnType getType() {
				return ColumnType.NUMBER;
			}
			@Override
			public boolean isPrimaryKey() {
				return true;
			}
			@Override
			public boolean isNotNull() {
				return true;
			}
		},

		USER_ID {
			@Override
			public String getName() {
				return "userId";
			}
			@Override
			public ColumnType getType() {
				return ColumnType.TEXT;
			}
			@Override
			public boolean isPrimaryKey() {
				return false;
			}
			@Override
			public boolean isNotNull() {
				return true;
			}
		},

		PASSWORD {
			@Override
			public String getName() {
				return "password";
			}
			@Override
			public ColumnType getType() {
				return ColumnType.TEXT;
			}
			@Override
			public boolean isPrimaryKey() {
				return false;
			}
			@Override
			public boolean isNotNull() {
				return false;
			}
		},

		USER_NAME {
			@Override
			public String getName() {
				return "userName";
			}
			@Override
			public ColumnType getType() {
				return ColumnType.TEXT;
			}
			@Override
			public boolean isPrimaryKey() {
				return false;
			}
			@Override
			public boolean isNotNull() {
				return false;
			}
		};
	};

	public SampleDBAdapter(Context context){
		super(context, Sample.class, SampleColumn.values());
	}

	public void insert(Sample sample) {
		super.insert(sample);
	}

	public void update(Sample sample) {
		WhereBuilder builder = new WhereBuilder();
		builder.eq(SampleColumn.USER_ID.getName(), sample.getUserId());
		super.update(builder, sample);
	}

	public Sample selectById(String userId){
		WhereBuilder builder = new WhereBuilder();
		builder.eq(SampleColumn.USER_ID.getName(), userId);
		return super.selectById(builder);
	}

	public List<Sample> selectByUserName(String userName){
		WhereBuilder builder = new WhereBuilder();
		builder.eq(SampleColumn.USER_NAME.getName(), userName);
		return super.selectBy(builder, SampleColumn.USER_ID.getName());
	}

	public List<Sample> selectAll(){
		return super.selectAll(SampleColumn.USER_ID.getName());
	}

	public boolean deleteByUserId(String userId){
		WhereBuilder builder = new WhereBuilder();
		builder.eq(SampleColumn.USER_ID.getName(), userId);
		return super.deleteBy(builder);
	}

	public boolean deleteAll(){
		return super.deleteAll();
	}
}
