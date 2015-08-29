package com.kickthecanclient.beans.sample;

import lombok.Data;

/**
 * お試しサーバ結果受け取り用Bean.
 *
 * @author ebihara
 */
@Data
public class SampleResponceBean {

	private String userId;
	private String password;
	private String userName;
}
