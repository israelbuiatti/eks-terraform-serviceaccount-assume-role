package com.example.circuitbreaker.service;

import com.example.circuitbreaker.model.Model1;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Component
@Log4j2
public class Service1 {

	@Autowired
	private RestTemplate template;

	@HystrixCommand(groupKey = "group1", commandKey = "command1", fallbackMethod = "fallBack", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
	})
	public Model1 service1() {

		String response = template.getForObject("http://localhost:9001/circuit-breaker/200/1000", String.class);

		Model1 model1 = new Model1();
		model1.setMessage(response);

		log.info("SERVICE1::: {} - {}", new Date(), response);

		return model1;
	}

	public Model1 fallBack() {

		String message = "service gateway failed 1...";

		log.error("SERVICE1::: {} - {}", new Date(), message);

		Model1 model1 = new Model1();
		model1.setMessage(message);
		model1.setStatus(false);

		return model1;

	}

}
