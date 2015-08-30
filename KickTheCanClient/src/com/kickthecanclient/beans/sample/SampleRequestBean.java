package com.kickthecanclient.beans.sample;

import lombok.Data;

/**
 * お試しサーバ送信用Bean.
 *
 * @author ebihara
 */
@Data
public class SampleRequestBean {

	private int id;
	private String userId;
	private String password;
	private String userName;

	public SampleRequestBean (String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public SampleRequestBean (int id, String userId, String password, String userName) {
		this.id = id;
		this.userId = userId;
		this.password = password;
		this.userName = userName;
	}
}
