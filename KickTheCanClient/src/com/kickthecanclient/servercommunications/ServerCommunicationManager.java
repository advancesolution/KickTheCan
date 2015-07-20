package com.kickthecanclient.servercommunications;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kickthecanclient.beans.serverCommunication.ServerCommunicationBean;
import com.kickthecanclient.constants.ServerCommunicationConst;
import com.kickthecanclient.constants.StringConst;

/**
 * サーバ通信処理呼び出し用クラス.
 */
public class ServerCommunicationManager<T> {

	@SuppressWarnings("unchecked")
	public T call(ServerCommunicationBean request) {
		T result = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(request.getRequestParams());
			AsyncProcess asyncProcess = new AsyncProcess();
			AsyncTask<String,Integer,String> responce = asyncProcess.execute(getUrl(request), json);
			result = (T) mapper.readValue(responce.get(), request.getBean());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private String getUrl(ServerCommunicationBean request) {
		StringBuilder sb = new StringBuilder();
		sb.append(ServerCommunicationConst.BASE_URL);
		sb.append(request.getActivity().getSimpleName().replace(ServerCommunicationConst.ACTIVITY_POST_POSITION, StringConst.EMPTY));
		sb.append(ServerCommunicationConst.SERVLET_POST_POSITION);
		return sb.toString();
	}
}
