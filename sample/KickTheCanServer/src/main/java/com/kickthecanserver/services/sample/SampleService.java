package com.kickthecanserver.services.sample;

import com.kickthecanserver.beans.sample.SampleRequestBean;
import com.kickthecanserver.beans.sample.SampleResponceBean;

/**
 * お試しサービスクラス.
 */
public interface SampleService {

	public SampleResponceBean getUserData(SampleRequestBean request);
}
