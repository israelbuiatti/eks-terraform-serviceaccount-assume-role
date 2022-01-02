//snippet-sourcedescription:[GetSecretValue.java demonstrates how to get the value of a secret from AWS Secrets Manager.]
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

package com.example.awssdk.secrets;

//snippet-start:[secretsmanager.java2.get_secret.import]
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.auth.StsAssumeRoleCredentialsProvider;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;
//snippet-end:[secretsmanager.java2.get_secret.import]

/**
 * To run this AWS code example, ensure that you have setup your development environment, including your AWS credentials.
 *
 * For information, see this documentation topic:
 *
 *https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */

public class GetSecretValue {

    public static void main(String[] args) {

        final String USAGE = "\n" +
                "Usage:\n" +
                "    <secretName> \n\n" +
                "Where:\n" +
                "    secretName - the name of the secret (for example, tutorials/MyFirstSecret). \n";


        Region region = Region.US_EAST_1;

        String roleArn = "arn:aws:iam::044712912013:role/s3-role";
        String roleSessionName = "sessionName";

        final AssumeRoleRequest assumeRoleRequest = AssumeRoleRequest.builder().roleSessionName(roleSessionName).roleArn(roleArn).build();
        final StsClient stsClient = StsClient.builder().region(Region.US_EAST_1).build();
        final StsAssumeRoleCredentialsProvider stsAssumeRoleCredentialsProvider = StsAssumeRoleCredentialsProvider.builder().stsClient(stsClient).refreshRequest(assumeRoleRequest).build();



        String secretName = "dev/user/israel";
        SecretsManagerClient secretsClient = SecretsManagerClient.builder()
                .credentialsProvider(stsAssumeRoleCredentialsProvider)
                .region(region)
                .build();

        getValue(secretsClient, secretName);
        secretsClient.close();
    }

    //snippet-start:[secretsmanager.java2.get_secret.main]
    public static void getValue(SecretsManagerClient secretsClient,String secretName) {

        try {
            GetSecretValueRequest valueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

            GetSecretValueResponse valueResponse = secretsClient.getSecretValue(valueRequest);
            String secret = valueResponse.secretString();
            System.out.println(secret);

        } catch (SecretsManagerException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
    //snippet-end:[secretsmanager.java2.get_secret.main]
}
