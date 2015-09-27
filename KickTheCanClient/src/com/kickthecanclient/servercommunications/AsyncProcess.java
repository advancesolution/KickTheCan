package com.kickthecanclient.servercommunications;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

import com.kickthecanclient.enums.CharacterCode;

/**
 * サーバ通信用クラス.
 *
 * @author ebihara
 */
@SuppressWarnings("deprecation")
public class AsyncProcess extends AsyncTask<String, Integer, String> {

	@Override
	protected String doInBackground(String... contents) {
		String result = null;
		try {
			ArrayList <NameValuePair> params = new ArrayList <>();
			params.add(new BasicNameValuePair("request", contents[1]));
			HttpPost post = new HttpPost(contents[0]);
			post.setEntity(new UrlEncodedFormEntity(params, CharacterCode.UTF_8.getValue()));
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse responce = httpClient.execute(post);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			responce.getEntity().writeTo(outputStream);
			result = outputStream.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return result;
	}
}

