package com.example.awssdk.controller;

import com.example.awssdk.settings.SecretsSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SecretController {

	@Autowired
	private SecretsSettings secrets;

	@GetMapping("/teste")
	public ResponseEntity teste() {

		List<Object> list = new ArrayList<>();
		list.add(secrets.getIsrael());
		list.add(secrets.getFuncionaltest());
		list.add(secrets.getListBucket());

		return ResponseEntity.ok(list);
	}

}
