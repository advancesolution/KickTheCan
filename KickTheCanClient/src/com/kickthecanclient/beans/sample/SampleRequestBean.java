package com.kickthecanclient.beans.sample;

import lombok.Data;

/**
 * お試しサーバ送信用Bean.
 */
@Data
public class SampleRequestBean {

	private String userId;
	private String password;

	public SampleRequestBean (String userId, String password) {
		this.userId = userId;
		this.password = password;
	}
}
