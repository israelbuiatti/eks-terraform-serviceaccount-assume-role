package com.example.awssdk.service;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.securitytoken.model.GetCallerIdentityRequest;
import com.amazonaws.services.securitytoken.model.GetCallerIdentityResult;
import com.example.awssdk.settings.AwsCredentialsSettings;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Log4j2
public class SecretService {

    @Autowired
    private AwsCredentialsSettings awsCredentialsSettings;

    public String getSecretValue(String secret)  {

        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secret);
        GetSecretValueResult getSecretValueResult = awsCredentialsSettings.getSecretClient().getSecretValue(getSecretValueRequest);

        String result = getSecretValueResult.getSecretString();

        return result;

    }

}
