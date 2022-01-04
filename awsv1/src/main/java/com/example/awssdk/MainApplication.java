package com.example.circuitbreaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableHystrix
@EnableHystrixDashboard
public class MainApplication {

	public static void main(String[] args) {


		SpringApplication.run(MainApplication.class, args);

		new ListS3().execute();


	}

	@Bean
	public RestTemplate template() {
		return new RestTemplate();
	}

}
