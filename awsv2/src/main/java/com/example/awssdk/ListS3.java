package com.example.awssdk;

import com.example.awssdk.settings.SecretsSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * To run this AWS code example, ensure that you have setup your development environment, including your AWS credentials.
 *
 * For information, see this documentation topic:
 *
 *https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */

@Component
public class ListS3 {

    @Autowired
    private SecretsSettings secrets;

    @PostConstruct
    public void init() {

//        Region region = Region.US_EAST_1;

//        String roleArn = "arn:aws:iam::044712912013:role/s3-role";
//        String roleSessionName = "sessionName";
//
//        final AssumeRoleRequest assumeRoleRequest = AssumeRoleRequest.builder().roleSessionName(roleSessionName).roleArn(roleArn).build();
//        final StsClient stsClient = StsClient.builder().region(Region.US_EAST_1).build();
//        final StsAssumeRoleCredentialsProvider stsAssumeRoleCredentialsProvider = StsAssumeRoleCredentialsProvider.builder().stsClient(stsClient).refreshRequest(assumeRoleRequest).build();

//        System.out.println(StsClient.create().getCallerIdentity().toString());


        S3Client s3 = S3Client.builder()
                .region(Region.US_EAST_1)
                //.credentialsProvider(stsAssumeRoleCredentialsProvider)
                .build();

        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        ListBucketsResponse listBucketsResponse = s3.listBuckets(listBucketsRequest);

        List<String> list = new ArrayList<>();

        listBucketsResponse.buckets().stream().forEach(x -> list.add(x.name()));

        secrets.setListBucket(list);


    }

}
