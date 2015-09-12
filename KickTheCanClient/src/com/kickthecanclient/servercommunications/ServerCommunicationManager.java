package com.kickthecanclient.servercommunications;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kickthecanclient.beans.ServerCommunicationBean;
import com.kickthecanclient.utils.StringUtil;

/**
 * サーバ通信処理呼び出し用クラス.
 *
 * @author ebihara
 */
public class ServerCommunicationManager<T> {

	public static final String BASE_URL="http://10.0.2.2:8080/kickthecanserver";

	@SuppressWarnings("unchecked")
	public T call(ServerCommunicationBean request) {
		T result = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(request.getRequestParams());
			AsyncProcess asyncProcess = new AsyncProcess();
			AsyncTask<String,Integer,String> responce = asyncProcess.execute(
					StringUtil.join(BASE_URL, request.getUrl()), json);
			result = (T) mapper.readValue(responce.get(), request.getBean());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
