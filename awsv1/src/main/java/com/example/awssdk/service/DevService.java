package com.example.awssdk.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("dev")
@Log4j2
public class DevService implements Service {

	@Value("${AWS_DEFAULT_REGION}")
	private String clientRegion;

	public List<String> getList() {

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
				.withRegion(clientRegion)
				.build();

		List<String> list = new ArrayList<>();

		List<Bucket> buckets = s3Client.listBuckets();
		buckets.stream().forEach(x -> list.add(x.getName()));

		return list;
	}

}
