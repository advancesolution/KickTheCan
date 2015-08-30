package com.kickthecanserver.beans.sample;

import lombok.Data;

import org.apache.commons.beanutils.PropertyUtils;

import com.kickthecanserver.entities.Sample;

/**
 * お試しデータ受け取り用Bean.
 *
 * @author ebihara
 */
@Data
public class SampleRequestBean {

	private int id;
	private String userId;
	private String password;
	private String userName;

	public Sample toEntity() {
		Sample sample = new Sample();
		try {
			PropertyUtils.copyProperties(sample, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sample;
	}
}
