package com.kickthecanclient.servercommunications;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

import com.kickthecanclient.constants.CommonConst;

/**
 * サーバ通信用クラス.
 */
@SuppressWarnings("deprecation")
public class AsyncProcess extends AsyncTask<String, Integer, String> {

	public static final String ACTIVITY_POST_POSITION = "MainActivity";
	public static final String REQUEST_KEY = "request";

	@Override
	protected String doInBackground(String... contents) {
		String result = null;
		try {
			ArrayList <NameValuePair> params = new ArrayList <>();
			params.add(new BasicNameValuePair(REQUEST_KEY, contents[1]));
			HttpPost post = new HttpPost(contents[0]);
			post.setEntity(new UrlEncodedFormEntity(params, CommonConst.CHARACTER_CODE_UTF_8));
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse responce = httpClient.execute(post);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			responce.getEntity().writeTo(outputStream);
			result = outputStream.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
}

