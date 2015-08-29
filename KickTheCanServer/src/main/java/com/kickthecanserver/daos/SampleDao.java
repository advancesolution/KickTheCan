package com.kickthecanserver.daos;

import com.kickthecanserver.entities.Sample;

/**
 * お試しDaoクラス.
 *
 * @author ebihara
 */
public interface SampleDao {

	public Sample get(String userId, String password);
}
