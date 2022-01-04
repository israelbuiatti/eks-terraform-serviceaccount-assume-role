package com.example.circuitbreaker.controller;

import com.example.circuitbreaker.dto.ResponseDto;
import com.example.circuitbreaker.model.Model1;
import com.example.circuitbreaker.model.Model2;
import com.example.circuitbreaker.model.Model3;
import com.example.circuitbreaker.service.Service1;
import com.example.circuitbreaker.service.Service2;
import com.example.circuitbreaker.service.Service3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@Autowired
	private Service1 service1;

	@Autowired
	private Service2 service2;

	@Autowired
	private Service3 service3;

	@GetMapping("/teste")
	public ResponseEntity<ResponseDto> teste() {
		Model1 model1 = service1.service1();
		Model2 model2 = service2.service2();
		Model3 model3 = service3.service3();

		ResponseDto response = new ResponseDto(model1, model2, model3);

		return ResponseEntity.ok(response);

	}


}
