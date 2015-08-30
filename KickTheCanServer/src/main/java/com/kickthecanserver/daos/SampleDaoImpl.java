package com.kickthecanserver.daos;

import org.springframework.stereotype.Component;

import com.kickthecanserver.daos.SampleDaoImpl.SampleColumn;
import com.kickthecanserver.entities.Sample;

/**
 * お試しDao実装クラス.
 *
 * @author ebihara
 */
@Component
public class SampleDaoImpl extends BaseDao<Sample, SampleColumn> implements SampleDao {

	public enum SampleColumn implements BaseColumn {
		ID {
			@Override
			public String getName() {
				return "id";
			}
			@Override
			public boolean isPrimaryKey() {
				return true;
			}
		},

		USER_ID {
			@Override
			public String getName() {
				return "user_id";
			}
			@Override
			public boolean isPrimaryKey() {
				return false;
			}
		},

		PASSWORD {
			@Override
			public String getName() {
				return "password";
			}
			@Override
			public boolean isPrimaryKey() {
				return false;
			}
		},

		USER_NAME {
			@Override
			public String getName() {
				return "user_name";
			}
			@Override
			public boolean isPrimaryKey() {
				return false;
			}
		};
	};

	public SampleDaoImpl() {
		super(Sample.class, SampleColumn.values());
	}

	public void insert(Sample sample) {
		super.insert(sample);
	}

	public void update(Sample sample) {
		super.update(sample);
	}

	public Sample get(String userId, String password) {
		WhereBuilder builder = new WhereBuilder();
		builder.eq(SampleColumn.USER_ID.getName(), userId).and().eq(SampleColumn.PASSWORD.getName(), password);
		Sample sample = super.selectBySingleResult(builder.getCondition());
		return sample;
	}
}
