package com.kickthecanclient.beans.serverCommunication;

import lombok.Data;

/**
 * サーバ通信用Bean.
 */
@Data
public class ServerCommunicationBean {

	private Class<?> activity;
	private Class<?> bean;
	private Object requestParams;

	public ServerCommunicationBean (Class<?> activity, Object requestParams, Class<?> bean) {
		this.activity = activity;
		this.bean = bean;
		this.requestParams = requestParams;
	}
}
