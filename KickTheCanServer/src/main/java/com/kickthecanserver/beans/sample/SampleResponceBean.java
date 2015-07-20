package com.kickthecanserver.beans.sample;

import lombok.Data;

import com.kickthecanserver.entities.Sample;

/**
 * お試しクライアント返却用Bean.
 */
@Data
public class SampleResponceBean {

	private Sample userData;

	public SampleResponceBean (Sample userData) {
		this.userData = userData;
	}
}
