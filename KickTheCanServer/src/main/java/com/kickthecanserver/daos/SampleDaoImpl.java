package com.kickthecanserver.daos;

import org.springframework.stereotype.Component;

import com.kickthecanserver.entities.Sample;

/**
 * お試しDao実装クラス.
 *
 * @author ebihara
 */
@Component
public class SampleDaoImpl extends BaseDao<Sample> implements SampleDao {

	public SampleDaoImpl() {
		super(Sample.class);
	}

	@Override
	public void insert(Sample sample) {
		super.insert(sample);
	}

	@Override
	public void update(Sample sample) {
		super.update(sample);
	}

	@Override
	public void deleteBy(String userId, String password) {
		WhereBuilder builder = new WhereBuilder();
		builder.eq("user_id", userId).and().eq("password", password);
		super.deleteBy(builder.getCondition());
	}

	@Override
	public Sample selectBy(String userId, String password) {
		WhereBuilder builder = new WhereBuilder();
		builder.eq("user_id", userId).and().eq("password", password);
		Sample sample = super.selectById(builder.getCondition());
		return sample;
	}
}
