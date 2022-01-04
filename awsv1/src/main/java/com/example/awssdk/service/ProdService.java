package com.example.awssdk.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.Bucket;
import com.example.awssdk.settings.AwsCredentialsSettings;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("prod")
@Log4j2
public class ProdService implements Service {

	@Autowired
	private AwsCredentialsSettings awsCredentialsSettings;

	public List<String> getList() {

		List<String> list = new ArrayList<>();

		try {
			List<Bucket> buckets = awsCredentialsSettings.getS3Client().listBuckets();
			buckets.stream().forEach(x -> list.add(x.getName()));
		}
        catch(AmazonServiceException e) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			System.out.println(".");
			System.out.println(".");
			System.out.println(".");
			System.out.println("Entrou1");
			System.out.println(e);
			System.out.println(e.getCause());
			e.printStackTrace();
		}
        catch(SdkClientException e) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			System.out.println(".");
			System.out.println(".");
			System.out.println(".");
			System.out.println("Entrou2");
			System.out.println(e);
			System.out.println(e.getCause());
			e.printStackTrace();

		}
		return list;
	}

}
