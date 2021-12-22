package com.example.circuitbreaker.service;

import com.example.circuitbreaker.model.Model3;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Component
@Log4j2
public class Service3 {

	@Autowired
	private RestTemplate template;

	@HystrixCommand(groupKey = "group1", commandKey = "command3", fallbackMethod = "fallBack", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000")
	})
	public Model3 service3() {

		Model3 model3 = new Model3();

		String response = template.getForObject("http://localhost:9003/circuit-breaker/200/10000", String.class);
		model3.setMessage(response);
		log.info("SERVICE3::: {} - {}", new Date(), response);

		return model3;
	}

	public Model3 fallBack() throws Exception {

		String message = "service gateway failed 3...";

		log.error("SERVICE3 fallback::: {} - {}", new Date(), message);

		Model3 model3 = new Model3();
		model3.setMessage(message);
		model3.setStatus(false);

		return model3;
	}

}
