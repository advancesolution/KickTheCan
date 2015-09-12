package com.kickthecanserver.entities;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * お試しエンティティ.
 *
 * @author ebihara
 */
@Data
@Table(name="sample")
public class Sample {

	@Id
	private int id;
	private String userId;
	private String password;
	private String userName;
}
