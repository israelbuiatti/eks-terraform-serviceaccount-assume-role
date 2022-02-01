package com.example.awssdk.controller;

import com.example.awssdk.service.S3Service;
import com.example.awssdk.service.SecretService;
import com.example.awssdk.service.StsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

	@Autowired
	private S3Service s3service;

	@Autowired
	private StsService stsService;

	@Autowired
	private SecretService secretService;


	@GetMapping("/buckets")
	public ResponseEntity buckets() {
		List<String> list = s3service.getList();
		return ResponseEntity.ok(list);
	}

	@GetMapping("/me")
	public ResponseEntity me() {
		String me = s3service.getCallerIdentity();
		return ResponseEntity.ok(me);
	}

	@GetMapping("/secret")
	public ResponseEntity secret() {
		String secret = secretService.getSecretValue("dev/user/israel");
		return ResponseEntity.ok(secret);
	}

	@GetMapping("/renew")
	public ResponseEntity renew() {
		s3service.renew();
		return ResponseEntity.ok("OK");
	}

}
