package com.kickthecanserver.beans;

import lombok.Data;

import com.kickthecanserver.entities.Sample;

/**
 * お試しクライアント返却用Bean.
 *
 * @author ebihara
 */
@Data
public class SampleResponceBean {

	private int id;
	private String userId;
	private String password;
	private String userName;

	public SampleResponceBean (Sample userData) {
		this.id = userData.getId();
		this.userId = userData.getUserId();
		this.password = userData.getPassword();
		this.userName = userData.getUserName();
	}
}
