package com.kickthecanclient.entities;

import javax.persistence.Table;

import lombok.Data;

/**
 * お試し版Entity.
 *
 * @author ebihara
 */
@Data
@Table(name="sample")
public class Sample {

	private int id;
	private String userId;
	private String password;
	private String userName;
}