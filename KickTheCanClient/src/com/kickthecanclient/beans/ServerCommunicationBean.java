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
	private Object requestParams;

	public ServerCommunicationBean (String url, Object requestParams) {
		this.url = url;
		this.requestParams = requestParams;
	}
}
