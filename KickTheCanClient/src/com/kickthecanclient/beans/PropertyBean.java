package com.kickthecanclient.beans;

import java.lang.reflect.Method;

import lombok.Data;

import com.kickthecanclient.utils.CaseUtil;

/**
 * プロパティ定義用Bean.
 *
 * @author ebihara
 */
@Data
public class PropertyBean {

	private String name;
	private Method readMethod;
	private Method writeMethod;

	public PropertyBean(String name, Method read, Method write) {
		this.name = CaseUtil.toLowerCamel(name);
		this.readMethod = read;
		this.writeMethod = write;
	}
}

