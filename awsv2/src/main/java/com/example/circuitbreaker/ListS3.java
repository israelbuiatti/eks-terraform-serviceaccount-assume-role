package com.example.circuitbreaker;//snippet-sourcedescription:[ListSecrets.java demonstrates how to list all of the secrets that are stored by Secrets Manager.]
//snippet-keyword:[AWS SDK for Java v2]
//snippet-keyword:[Code Sample]
//snippet-service:[AWS Secrets Manager]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[09/27/2021]
//snippet-sourceauthor:[scmacdon-AWS]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/



import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.auth.StsAssumeRoleCredentialsProvider;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;

/**
 * To run this AWS code example, ensure that you have setup your development environment, including your AWS credentials.
 *
 * For information, see this documentation topic:
 *
 *https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */

public class ListS3 {

    public void execute() {

        Region region = Region.US_EAST_1;

//        String roleArn = "arn:aws:iam::044712912013:role/s3-role";
//        String roleSessionName = "sessionName";
//
//        final AssumeRoleRequest assumeRoleRequest = AssumeRoleRequest.builder().roleSessionName(roleSessionName).roleArn(roleArn).build();
        final StsClient stsClient = StsClient.builder().region(Region.US_EAST_1).build();
//        final StsAssumeRoleCredentialsProvider stsAssumeRoleCredentialsProvider = StsAssumeRoleCredentialsProvider.builder().stsClient(stsClient).refreshRequest(assumeRoleRequest).build();

        System.out.println(StsClient.create().getCallerIdentity().toString());


        S3Client s3 = S3Client.builder()
                .region(Region.US_EAST_1)
                //.credentialsProvider(stsAssumeRoleCredentialsProvider)
                .build();

        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        ListBucketsResponse listBucketsResponse = s3.listBuckets(listBucketsRequest);
        listBucketsResponse.buckets().stream().forEach(x -> System.out.println(x.name()));

    }

}
