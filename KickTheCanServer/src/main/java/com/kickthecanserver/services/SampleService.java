package com.kickthecanserver.services;

import com.kickthecanserver.beans.SampleRequestBean;
import com.kickthecanserver.beans.SampleResponceBean;

/**
 * お試しサービスクラス.
 *
 * @author ebihara
 */
public interface SampleService {

	SampleResponceBean getUserData(SampleRequestBean request);

	void deleteUserData(SampleRequestBean request);

	void insertUserData(SampleRequestBean request);

	void updateUserData(SampleRequestBean request);
}
