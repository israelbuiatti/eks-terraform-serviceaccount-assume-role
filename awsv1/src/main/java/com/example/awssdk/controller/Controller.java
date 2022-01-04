package com.example.awssdk.controller;

import com.example.awssdk.AwsConfig;
import com.example.awssdk.AwsGetCallerIdentity;
import com.example.awssdk.service.Service;
import com.example.awssdk.settings.AwsCredentialsSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

	@Autowired
	private Service service;

	@Autowired
	private AwsCredentialsSettings awsCredentialsSettings;

	@Autowired
	AwsConfig awsConfig;

	@Autowired
	AwsGetCallerIdentity awsGetCallerIdentity;

	@GetMapping("/teste")
	public ResponseEntity teste() {

		//Check token is expired
		try {
			awsGetCallerIdentity.getCallerIdentity();
		} catch (Exception e) {
			awsConfig.renew();
		}

		List<String> list = service.getList();
		return ResponseEntity.ok(list);
	}

	@GetMapping("/renew")
	public ResponseEntity renew() {

		System.out.println("AWS_ROLE_ARN= " + awsCredentialsSettings.getRoleArn());
		System.out.println("AWS_WEB_IDENTITY_TOKEN_FILE= " + awsCredentialsSettings.getServiceAccountTokenFile());
		System.out.println("AWS_DEFAULT_REGION= " + awsCredentialsSettings.getClientRegion());

		awsConfig.renew();

		return ResponseEntity.ok("OK");
	}


}
