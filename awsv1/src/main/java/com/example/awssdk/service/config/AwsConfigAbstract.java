package com.example.awssdk.service.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.example.awssdk.settings.AwsCredentialsSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public abstract class AwsConfigAbstract implements AwsConfig {

    @Autowired
    private AwsCredentialsSettings awsCredentialsSettings;

    public abstract AWSCredentialsProvider getProvider();

    @Override
    public void renew() {

        //S3
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(getProvider())
                .withRegion(awsCredentialsSettings.getRegion())
                .build();

        awsCredentialsSettings.setS3Client(s3Client);


        //STS
        AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard()
                .withCredentials(getProvider())
                .withRegion(awsCredentialsSettings.getRegion())
                .build();

        awsCredentialsSettings.setStsClient(stsClient);


        //SECRET MANAGER
        AWSSecretsManager secretClient = AWSSecretsManagerClientBuilder.standard()
                .withCredentials(getProvider())
                .withRegion(awsCredentialsSettings.getRegion())
                .build();

        awsCredentialsSettings.setSecretClient(secretClient);

    }

}

