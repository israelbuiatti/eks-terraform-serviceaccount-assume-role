package com.example.awssdk.service;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

@Component
public class SecretService {

    public String getSecretValue(String secret)  {


        SecretsManagerClient secretsClient = SecretsManagerClient.builder()
                .region(Region.US_EAST_1)
                .build();

        String response = getValue(secretsClient, secret);

        secretsClient.close();

        return response;
    }

    public static String getValue(SecretsManagerClient secretsClient,String secretName) {

        try {
            GetSecretValueRequest valueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

            GetSecretValueResponse valueResponse = secretsClient.getSecretValue(valueRequest);
            String secret = valueResponse.secretString();

            return secret;

        } catch (SecretsManagerException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }

        return null;
    }
}
