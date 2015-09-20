package com.kickthecanclient.dbadapters;

import java.util.List;

import android.content.Context;

import com.kickthecanclient.entities.Sample;

/**
 * お試しSQLite版Daoクラス.
 *
 * @author ebihara
 */
public class SampleDBAdapter extends BaseDBAdapter<Sample> {

	public SampleDBAdapter(Context context){
		super(context, Sample.class);
	}

	public void insert(Sample sample) {
		super.insert(sample);
	}

	public void update(Sample sample) {
		super.update(sample);
	}

	public Sample selectById(String userId){
		WhereBuilder builder = new WhereBuilder();
		builder.eq("user_id", userId);
		return super.selectById(builder);
	}

	public List<Sample> selectByUserName(String userName){
		WhereBuilder builder = new WhereBuilder();
		builder.eq("user_name", userName);
		return super.selectBy(builder, "user_id");
	}

	public List<Sample> selectAll(){
		return super.selectAll("user_id");
	}

	public boolean deleteByUserId(String userId){
		WhereBuilder builder = new WhereBuilder();
		builder.eq("user_id", userId);
		return super.deleteBy(builder);
	}

	public boolean deleteAll(){
		return super.deleteAll();
	}
}
