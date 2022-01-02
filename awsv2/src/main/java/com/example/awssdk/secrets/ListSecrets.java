//snippet-sourcedescription:[ListSecrets.java demonstrates how to list all of the secrets that are stored by Secrets Manager.]
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

//snippet-start:[secretsmanager.java2.list_secrets.import]

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.ListSecretsResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretListEntry;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.auth.StsAssumeRoleCredentialsProvider;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;

import java.util.List;

//snippet-end:[secretsmanager.java2.list_secrets.import]

/**
 * To run this AWS code example, ensure that you have setup your development environment, including your AWS credentials.
 *
 * For information, see this documentation topic:
 *
 *https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */

public class ListSecrets {

    public static void main(String[] args) {

        Region region = Region.US_EAST_1;

        String roleArn = "arn:aws:iam::044712912013:role/s3-role";
        String roleSessionName = "sessionName";

        final AssumeRoleRequest assumeRoleRequest = AssumeRoleRequest.builder().roleSessionName(roleSessionName).roleArn(roleArn).build();
        final StsClient stsClient = StsClient.builder().region(Region.US_EAST_1).build();
        final StsAssumeRoleCredentialsProvider stsAssumeRoleCredentialsProvider = StsAssumeRoleCredentialsProvider.builder().stsClient(stsClient).refreshRequest(assumeRoleRequest).build();


        SecretsManagerClient secretsClient = SecretsManagerClient.builder()
                .credentialsProvider(stsAssumeRoleCredentialsProvider)
                .region(region)
                .build();

        listAllSecrets(secretsClient);
        secretsClient.close();
    }

    //snippet-start:[secretsmanager.java2.list_secrets.main]
    public static void listAllSecrets(SecretsManagerClient secretsClient) {

        try {

            ListSecretsResponse secretsResponse = secretsClient.listSecrets();
            List<SecretListEntry> secrets = secretsResponse.secretList();

            for (SecretListEntry secret: secrets) {
                System.out.println("The secret name is "+secret.name());
                System.out.println("The secret descreiption is "+secret.description());
            }

        } catch (SecretsManagerException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
    //snippet-end:[secretsmanager.java2.list_secrets.main]
}
