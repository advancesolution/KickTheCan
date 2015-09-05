package com.kickthecanserver.daos;

import com.kickthecanserver.entities.Sample;

/**
 * お試しDaoクラス.
 *
 * @author ebihara
 */
public interface SampleDao {

	Sample selectBy(String userId, String password);

	void deleteBy(String userId, String password);

	void insert(Sample sample);

	void update(Sample sample);
}
