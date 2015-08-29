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

	public enum SampleColumn implements BaseColumn {
		ID {
			@Override
			public String getName() {
				return "id";
			}
		},

		USER_ID {
			@Override
			public String getName() {
				return "user_id";
			}
		},

		PASSWORD {
			@Override
			public String getName() {
				return "password";
			}
		},

		USER_NAME {
			@Override
			public String getName() {
				return "user_name";
			}
		};
	};

	public SampleDaoImpl() {
		super(Sample.class);
	}

	public Sample get(String userId, String password) {
		WhereBuilder builder = new WhereBuilder();
		builder.eq(SampleColumn.USER_ID.getName(), userId).and().eq(SampleColumn.PASSWORD.getName(), password);
		Sample sample = super.selectBySingleResult(builder.getCondition());
		return sample;
	}
}
