package com.kickthecanserver.entities;

import lombok.Data;

import org.springframework.stereotype.Component;

/**
 * お試しエンティティ.
 */
@Data
@Component("sample")
public class Sample {

	private int id;
	private String userId;
	private String password;
	private String userName;
}
