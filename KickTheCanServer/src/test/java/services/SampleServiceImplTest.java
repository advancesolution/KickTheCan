package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kickthecanserver.services.sample.SampleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/WEB-INF/spring-mvc-servlet.xml"})
public class SampleServiceImplTest{

	@Autowired
	private SampleService sampleService;

	@Test
	public void getUserData() {
		sampleService.getUserData(null);
	}
}
