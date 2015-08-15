package com.kickthecanserver.beans.sample;

import lombok.Data;

import com.kickthecanserver.entities.Sample;

/**
 * お試しクライアント返却用Bean.
 */
@Data
public class SampleResponceBean {

	private String userId;
	private String password;
	private String userName;

	public SampleResponceBean (Sample userData) {
		this.userId = userData.getUserId();
		this.password = userData.getPassword();
		this.userName = userData.getUserName();
	}
}
