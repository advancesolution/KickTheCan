package com.kickthecanserver.controllers
;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kickthecanserver.beans.sample.SampleRequestBean;
import com.kickthecanserver.servercommunications.ServerCommunicationManager;
import com.kickthecanserver.services.sample.SampleService;

/**
 * お試しコントローラー.
 */
@Controller
@RequestMapping("sample")
public class SampleController {

	@Autowired
	private SampleService sampleService;

	@RequestMapping("search")
	public void search(HttpServletRequest req, HttpServletResponse res) {
		ServerCommunicationManager<SampleRequestBean> manager = new ServerCommunicationManager<SampleRequestBean>();
		SampleRequestBean requestBean = manager.read(req, SampleRequestBean.class);
		manager.write(res, sampleService.getUserData(requestBean));
	}
}