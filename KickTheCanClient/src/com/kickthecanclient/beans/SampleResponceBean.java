package com.kickthecanclient.beans;

import lombok.Data;

import com.kickthecanclient.entities.Sample;
import com.kickthecanclient.utils.ApplicationUtil;
import com.kickthecanclient.utils.PropertyUtil;

/**
 * お試しサーバ結果受け取り用Bean.
 *
 * @author ebihara
 */
@Data
public class SampleResponceBean {

	private int id;
	private String userId;
	private String password;
	private String userName;

	public Sample toEntity() {
		Sample sample = new Sample();
		try {
			PropertyUtil.copyProperties(sample, this);
		} catch (Exception e) {
			ApplicationUtil.exceptionHandler(e);
		}
		return sample;
	}
}
