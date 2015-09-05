package com.kickthecanclient.beans;

import lombok.Data;

/**
 * サーバ通信用Bean.
 *
 * @author ebihara
 */
@Data
public class ServerCommunicationBean {

	private String url;
	private Class<?> bean;
	private Object requestParams;

	public ServerCommunicationBean (String url, Object requestParams, Class<?> bean) {
		this.url = url;
		this.bean = bean;
		this.requestParams = requestParams;
	}
}