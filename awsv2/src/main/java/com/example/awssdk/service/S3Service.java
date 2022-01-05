package com.example.awssdk.service;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class S3Service {

    public List<String> getList() {

        S3Client s3 = S3Client.builder()
                .region(Region.US_EAST_1)
                .build();

        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        ListBucketsResponse listBucketsResponse = s3.listBuckets(listBucketsRequest);

        List<String> list = new ArrayList<>();

        listBucketsResponse.buckets().stream().forEach(x -> list.add(x.name()));

        return list;

    }

}
