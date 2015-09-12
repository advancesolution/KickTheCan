package com.kickthecanserver.controllers
;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * コントローラー基底クラス.
 *
 * @author ebihara
 */
@Controller
public class BaseController<T> {

	@SuppressWarnings("unchecked")
	public T read(HttpServletRequest request, Class<?> beanClass) {
		T bean = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			bean = (T)mapper.readValue(request.getParameter("request"), beanClass);
		} catch (IOException e) {
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