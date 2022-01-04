package com.example.awssdk.service.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.Bucket;
import com.example.awssdk.settings.AwsCredentialsSettings;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class S3Service {

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
			e.printStackTrace();
		}
        catch(SdkClientException e) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			e.printStackTrace();
		}
		return list;
	}

}
