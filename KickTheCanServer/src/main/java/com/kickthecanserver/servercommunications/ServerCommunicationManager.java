package com.kickthecanserver.servercommunications;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kickthecanserver.beans.sample.SampleRequestBean;
import com.kickthecanserver.constants.ServerCommunicationConst;

/**
 * サーバ通信処理定義クラス.
 *
 * @author ebihara
 */
public class ServerCommunicationManager<T> {

	@SuppressWarnings("unchecked")
	public T read(HttpServletRequest request, Class<?> beanClass) {
		T bean = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			bean = (T)mapper.readValue(request.getParameter(ServerCommunicationConst.REQUEST_KEY), SampleRequestBean.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bean;
	}

	public void write(HttpServletResponse responce, Object o) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			responce.getOutputStream().write(mapper.writeValueAsString(o).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
