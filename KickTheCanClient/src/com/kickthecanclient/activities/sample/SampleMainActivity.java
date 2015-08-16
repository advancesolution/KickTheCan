package com.kickthecanclient.activities.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.kickthecanclient.beans.sample.SampleRequestBean;
import com.kickthecanclient.beans.sample.SampleResponceBean;
import com.kickthecanclient.beans.serverCommunication.ServerCommunicationBean;
import com.kickthecanclient.dbadapters.SampleDBAdapter;
import com.kickthecanclient.entities.Sample;
import com.kickthecanclient.servercommunications.ServerCommunicationManager;

/**
 * お試しActivity.
 */
public class SampleMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbAccess();
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

	private SampleResponceBean callServer(String userId, String password) {
		SampleRequestBean requestParams = new SampleRequestBean(userId, password);
		ServerCommunicationBean bean = new ServerCommunicationBean("/sample/search", requestParams, SampleResponceBean.class);
		ServerCommunicationManager<SampleResponceBean> manager = new ServerCommunicationManager<>();
		SampleResponceBean responce = manager.call(bean);
		return responce;
	}

	private Sample getEntity(SampleResponceBean responce) {
		Sample sample = new Sample();
		sample.setId(1);
		sample.setUserId(responce.getUserId());
		sample.setPassword(responce.getPassword());
		sample.setUserName(responce.getUserName());
		return sample;
	}

	private void dbAccess() {
		SampleDBAdapter sampleDBAdapter = new SampleDBAdapter(this);
		sampleDBAdapter.open();
		sampleDBAdapter.insert(getEntity(callServer("administrator", "admin")));
		sampleDBAdapter.update(getEntity(callServer("administrator", "admin2")));
		sampleDBAdapter.selectById("administrator");
		sampleDBAdapter.selectByUserName("dummy");
		sampleDBAdapter.selectAll();
		sampleDBAdapter.deleteByUserId("administrator");
		sampleDBAdapter.deleteAll();
		sampleDBAdapter.close();
	}
}
