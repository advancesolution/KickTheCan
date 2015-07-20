package com.kickthecanserver.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.kickthecanserver.beans.sample.SampleRequestBean;
import com.kickthecanserver.servercommunications.ServerCommunicationManager;
import com.kickthecanserver.services.sample.SampleService;

/**
 * お試しサーブレット.
 */
public class SampleServlet extends HttpServlet {

	@Autowired
	private SampleService sampleService;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ServerCommunicationManager<SampleRequestBean> manager = new ServerCommunicationManager<SampleRequestBean>();
		SampleRequestBean requestBean = manager.readRequest(req, SampleRequestBean.class);
		manager.writeResponce(res, sampleService.getUserData(requestBean));
	}
}