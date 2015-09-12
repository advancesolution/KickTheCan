package com.kickthecanclient.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.kickthecanclient.beans.SampleRequestBean;
import com.kickthecanclient.beans.SampleResponceBean;
import com.kickthecanclient.beans.ServerCommunicationBean;
import com.kickthecanclient.dbadapters.SampleDBAdapter;
import com.kickthecanclient.entities.Sample;
import com.kickthecanclient.servercommunications.ServerCommunicationManager;

/**
 * お試しActivity.
 *
 * @author ebihara
 */
public class SampleMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		callServer("/sample/insert", new SampleRequestBean(12, "testUserId02", "testPassword01", "testUserName01"));
		callServer("/sample/update", new SampleRequestBean(12, "testUserId05", "testPassword05", "testUserName05"));
		dbAccess(callServer("/sample/search", new SampleRequestBean(12, "testUserId05", "testPassword05", "testUserName05")).toEntity());
		callServer("/sample/delete", new SampleRequestBean(12, "testUserId05", "testPassword05", "testUserName05")).toEntity();
		setContentView(R.layout.activity_sample_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sample_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private SampleResponceBean callServer(String requestUrl, SampleRequestBean requestParam) {
		ServerCommunicationBean bean = new ServerCommunicationBean(requestUrl, requestParam, SampleResponceBean.class);
		ServerCommunicationManager<SampleResponceBean> manager = new ServerCommunicationManager<>();
		return manager.call(bean);
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
