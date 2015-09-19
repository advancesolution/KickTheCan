package com.kickthecanclient.servercommunications;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kickthecanclient.beans.ServerCommunicationBean;
import com.kickthecanclient.utils.PropertyFileUtil;
import com.kickthecanclient.utils.PropertyUtil;
import com.kickthecanclient.utils.StringUtil;

/**
 * サーバ通信処理呼び出し用クラス.
 *
 * @author ebihara
 */
public class ServerCommunicationManager<T> {

	public T call(ServerCommunicationBean request, Class<T> clazz) {
		T result = null;
		try {
			result = clazz.newInstance();
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(request.getRequestParams());
			AsyncProcess asyncProcess = new AsyncProcess();
			AsyncTask<String,Integer,String> responce = asyncProcess.execute(
					StringUtil.join(PropertyFileUtil.getProperty("server", "url"), request.getUrl()), json);
			PropertyUtil.copyProperties(result, mapper.readValue(responce.get(), clazz));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
