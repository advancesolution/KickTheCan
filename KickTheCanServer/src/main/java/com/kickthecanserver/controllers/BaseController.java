package com.kickthecanserver.controllers
;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * コントローラー基底クラス.
 *
 * @author ebihara
 */
@Controller
public class BaseController<T> {

	public T read(HttpServletRequest request, Class<T> beanClass) {
		T bean = null;
		try {
			bean = beanClass.newInstance();
			ObjectMapper mapper = new ObjectMapper();
			PropertyUtils.copyProperties(bean, mapper.readValue(request.getParameter("request"), beanClass));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	public void write(HttpServletResponse responce, Object o) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			responce.getOutputStream().write(mapper.writeValueAsString(o).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}