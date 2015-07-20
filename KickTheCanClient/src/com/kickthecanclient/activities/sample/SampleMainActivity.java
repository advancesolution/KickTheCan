package com.kickthecanclient.activities.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.kickthecanclient.beans.sample.SampleRequestBean;
import com.kickthecanclient.beans.sample.SampleResponceBean;
import com.kickthecanclient.beans.serverCommunication.ServerCommunicationBean;
import com.kickthecanclient.servercommunications.ServerCommunicationManager;

/**
 * お試しActivity.
 */
public class SampleMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		callServer();
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

	private SampleResponceBean callServer() {
		SampleRequestBean requestParams = new SampleRequestBean("user", "9999");
		ServerCommunicationBean bean = new ServerCommunicationBean(this.getClass(), requestParams, SampleResponceBean.class);
		ServerCommunicationManager<SampleResponceBean> manager = new ServerCommunicationManager<>();
		SampleResponceBean responce = manager.call(bean);
		return responce;
	}
}
