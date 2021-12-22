package com.example.circuitbreaker.service;

import com.example.circuitbreaker.model.Model2;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Component
@Log4j2
public class Service2 {

	@Autowired
	private RestTemplate template;

	@HystrixCommand(groupKey = "group1", commandKey = "command2", fallbackMethod = "fallBack", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")
	})
	public Model2 service2() {
		String response = template.getForObject("http://localhost:9002/circuit-breaker/200/11000", String.class);

		Model2 model2 = new Model2();
		model2.setMessage(response);

		log.info("SERVICE2::: {} - {}", new Date(), response);

		return model2;
	}

	public Model2 fallBack() {

		String message = "service gateway failed 2..";

		log.error("SERVICE2::: {} - {}", new Date(), message);


		Model2 model2 = new Model2();
		model2.setMessage(message);
		model2.setStatus(false);

		return model2;
	}

}
