package com.kickthecanclient.entities;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * お試し版Entity.
 *
 * @author ebihara
 */
@Data
@Table(name="sample")
public class Sample {

	@Id
	@NotNull
	private int id;
	@NotNull
	private String userId;
	private String password;
	private String userName;
}