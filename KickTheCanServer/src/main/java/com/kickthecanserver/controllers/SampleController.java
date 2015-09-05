package com.kickthecanserver.controllers
;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kickthecanserver.beans.SampleRequestBean;
import com.kickthecanserver.services.SampleService;

/**
 * お試しコントローラー.
 *
 * @author ebihara
 */
@Controller
@RequestMapping("sample")
public class SampleController extends BaseController<SampleRequestBean> {

	@Autowired
	private SampleService sampleService;

	@RequestMapping("insert")
	public void insert(HttpServletRequest req, HttpServletResponse res) {
		SampleRequestBean requestBean = super.read(req, SampleRequestBean.class);
		sampleService.insertUserData(requestBean);
	}

	@RequestMapping("delete")
	public void delete(HttpServletRequest req, HttpServletResponse res) {
		SampleRequestBean requestBean = super.read(req, SampleRequestBean.class);
		sampleService.deleteUserData(requestBean);
	}

	@RequestMapping("update")
	public void update(HttpServletRequest req, HttpServletResponse res) {
		SampleRequestBean requestBean = super.read(req, SampleRequestBean.class);
		sampleService.updateUserData(requestBean);
	}

	@RequestMapping("search")
	public void search(HttpServletRequest req, HttpServletResponse res) {
		SampleRequestBean requestBean = super.read(req, SampleRequestBean.class);
		super.write(res, sampleService.getUserData(requestBean));
	}
}