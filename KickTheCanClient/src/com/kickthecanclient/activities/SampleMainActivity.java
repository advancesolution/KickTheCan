package com.kickthecanclient.activities;

import android.os.Bundle;
import android.view.View;

import com.kickthecanclient.beans.SampleRequestBean;
import com.kickthecanclient.beans.SampleResponceBean;
import com.kickthecanclient.beans.ServerCommunicationBean;
import com.kickthecanclient.dbadapters.SampleDBAdapter;
import com.kickthecanclient.entities.Sample;
import com.kickthecanclient.servercommunications.ServerCommunicationManager;
import com.kickthecanclient.utils.DialogUtil;

/**
 * お試しActivity.
 *
 * @author ebihara
 */
public class SampleMainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_sample_main);
			registListener();
			callServer("/sample/insert", new SampleRequestBean(12, "testUserId01", "testPassword01", "testUserName01"));
			callServer("/sample/update", new SampleRequestBean(12, "testUserId05", "testPassword05", "testUserName05"));
			dbAccess(callServer("/sample/search", new SampleRequestBean(12, "testUserId05", "testPassword05", "testUserName05")).toEntity());
			callServer("/sample/delete", new SampleRequestBean(12, "testUserId05", "testPassword05", "testUserName05")).toEntity();
		} catch (Exception e) {
			DialogUtil.showErrorDialog(e);
		}
	}

	private void registListener() {
		findViewById(R.id.showErrorDialogButton).setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	DialogUtil.showErrorDialog(new Exception());
		    }
		});
	}

	private SampleResponceBean callServer(String requestUrl, SampleRequestBean requestParam) {
		ServerCommunicationBean bean = new ServerCommunicationBean(requestUrl, requestParam);
		ServerCommunicationManager<SampleResponceBean> manager = new ServerCommunicationManager<>();
		return manager.call(bean, SampleResponceBean.class);
	}

	private void dbAccess(Sample entity) {
		SampleDBAdapter sampleDBAdapter = new SampleDBAdapter(this);
		sampleDBAdapter.open();
		sampleDBAdapter.insert(entity);
		sampleDBAdapter.update(entity);
		sampleDBAdapter.selectById(entity.getUserId());
		sampleDBAdapter.selectByUserName(entity.getUserName());
		sampleDBAdapter.selectAll();
		sampleDBAdapter.deleteByUserId(entity.getUserId());
		sampleDBAdapter.deleteAll();
		sampleDBAdapter.close();
	}
}
