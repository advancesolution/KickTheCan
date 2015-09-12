package com.kickthecanserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kickthecanserver.beans.SampleRequestBean;
import com.kickthecanserver.beans.SampleResponceBean;
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
		SampleResponceBean responceBean = new SampleResponceBean(
				sampleDao.selectBy(request.getUserId(), request.getPassword()));
		return responceBean;
	}

	@Override
	public void deleteUserData(SampleRequestBean request) {
		sampleDao.deleteBy(request.getUserId(), request.getPassword());
	}

	@Override
	public void insertUserData(SampleRequestBean request) {
		sampleDao.insert(request.toEntity());
	}

	@Override
	public void updateUserData(SampleRequestBean request) {
		sampleDao.update(request.toEntity());
	}
}
