package com.example.awssdk.service;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class S3Service {

    public List<String> getList() {

        // If you need to set credentials manually (e.g from vault)
        // AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
        //        "your_access_key_id",
        //        "your_secret_access_key");

        S3Client s3 = S3Client.builder()
                //.credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.US_EAST_1)
                .build();

        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        ListBucketsResponse listBucketsResponse = s3.listBuckets(listBucketsRequest);


        List<String> list = new ArrayList<>();

        listBucketsResponse.buckets().stream().forEach(x -> list.add(x.name()));

        return list;

    }

}
