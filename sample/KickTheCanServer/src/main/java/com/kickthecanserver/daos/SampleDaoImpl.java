package com.kickthecanserver.daos;

import org.springframework.stereotype.Component;

import com.kickthecanserver.entities.Sample;

/**
 * お試しDao実装クラス.
 */
@Component
public class SampleDaoImpl implements SampleDao {

	@Override
	public Sample get(String userId, String password) {
		// べた書きはなくしたい。とりあえずのサンプル。
		//		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		//		return jdbcTemplate.queryForObject(StringUtil.join(
		//				"SELECT * FROM SAMPLE WHERE USER_ID = '", userId, "' AND PASSWORD = '", password, "'"), new BeanPropertyRowMapper<Sample>(Sample.class));

		// DBまだ使えないので一旦dummy返却
		Sample sample = new Sample();
		sample.setUserId(userId);
		sample.setPassword(password);
		sample.setUserName("dummy");
		return sample;
	}
}
