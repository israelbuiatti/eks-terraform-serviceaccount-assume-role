package com.example.awssdk.service;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.GetCallerIdentityResponse;
import software.amazon.awssdk.services.sts.model.StsException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class S3Service {

    Region region = Region.US_EAST_1;
    String container = "test-israel-awsv2";
    String prefix = "test";
    String file = "00bf1459-497f-4f97-9384-23b75d0960d9";
    S3Client s3Client;
    StsClient stsClient;

    public S3Service() {
        renew();
    }

    public void renew() {
        try {
            System.out.println("====================================");
            System.out.println("===== Renew " + new Date());
            System.out.println("====================================");
            s3Client = S3Client.builder().region(region).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //=======================================
    // LIST BUCKET
    //=======================================
    public List<String> getList() {

        List<String> list = new ArrayList<>();

        try {

            ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
            ListBucketsResponse listBucketsResponse = s3Client.listBuckets(listBucketsRequest);

            listBucketsResponse.buckets().stream().forEach(x -> list.add(x.name()));

        } catch(S3Exception e) {
             if (e.awsErrorDetails().errorCode().equals("ExpiredToken")) {
                 System.out.println("====================================");
                 System.out.println("===== ExpiredToken " + new Date());
                 System.out.println("====================================");
                 e.printStackTrace();
                 renew();
             }
        }

        return list;

    }

    //=======================================
    // HEAD OBJECT
    //=======================================
    public HeadObjectResponse headObject(String objectKey) {

        try {

            HeadObjectRequest objectRequest = HeadObjectRequest.builder()
                    .key(objectKey)
                    .bucket(container)
                    .build();

            HeadObjectResponse objectHead = s3Client.headObject(objectRequest);
            Map<String, String> metadata = objectHead.metadata();

            System.out.println(objectKey);
            System.out.println(metadata);

            return objectHead;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    //=======================================
    // LIST OBJECT
    //=======================================
    public List<String> listObject() {

        try {

            List<String> theFiles = new ArrayList<String>();

            ListObjectsRequest listObjects = ListObjectsRequest.builder()
                    .bucket(container)
                    .prefix(prefix)
                    .build();

            ListObjectsResponse res = s3Client.listObjects(listObjects);
            List<S3Object> objects = res.contents();

            objects.stream().forEach(x -> theFiles.add(x.key()));

            //theFiles.stream().forEach(System.out::println);

            for (String objectKey : theFiles) {
                headObject(objectKey);
            }

            return theFiles;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //=======================================
    // GET CALLER IDENTITY
    //=======================================
    public String getCallerIdentity() {
        try {

            stsClient = StsClient.builder().region(region).build();

            GetCallerIdentityResponse response = stsClient.getCallerIdentity();

            System.out.println("The user id is" +response.userId());
            System.out.println("The ARN value is" +response.arn());

            return response.arn();
        } catch (StsException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}
