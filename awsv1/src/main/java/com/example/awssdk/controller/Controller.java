package com.example.awssdk.controller;

import com.example.awssdk.service.SecretService;
import com.example.awssdk.service.config.AwsConfig;
import com.example.awssdk.service.S3Service;
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

	@Autowired
	private AwsConfig awsConfig;


	@GetMapping("/buckets")
	public ResponseEntity buckets() {

		//Check token is expired
		try {
			stsService.getCallerIdentity();
		} catch (Exception e) {
			awsConfig.renew();
		}

		List<String> list = s3service.getList();
		return ResponseEntity.ok(list);
	}

	@GetMapping("/me")
	public ResponseEntity me() {
		String me = stsService.getCallerIdentity();
		return ResponseEntity.ok(me);
	}

	@GetMapping("/secret")
	public ResponseEntity secret() {
		String secret = secretService.getSecretValue("dev/user/israel");
		return ResponseEntity.ok(secret);
	}

	@GetMapping("/renew")
	public ResponseEntity renew() {
		awsConfig.renew();
		return ResponseEntity.ok("Aws credentials renewed...");
	}

}
