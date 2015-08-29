package com.kickthecanserver.services.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kickthecanserver.beans.sample.SampleRequestBean;
import com.kickthecanserver.beans.sample.SampleResponceBean;
import com.kickthecanserver.daos.SampleDao;

/**
 * お試しサービス実装クラス.
 *
 * @author ebihara
 */
@Service
public class SampleServiceImpl implements SampleService {

	@Autowired
	private SampleDao sampleDao;

	@Override
	public SampleResponceBean getUserData(SampleRequestBean request) {
		SampleResponceBean responceBean = new SampleResponceBean(sampleDao.get(request.getUserId(), request.getPassword()));
		return responceBean;
	}
}
