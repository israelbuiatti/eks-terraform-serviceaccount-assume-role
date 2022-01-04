package com.example.awssdk;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.*;
import com.example.awssdk.settings.AwsCredentialsSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("prod")
public class AwsGetCallerIdentity {

    @Autowired
    private AwsCredentialsSettings awsCredentialsSettings;

    public void getCallerIdentity()  {

        AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentialsSettings.getAwsCredentials()))
                .withRegion(awsCredentialsSettings.getRegion())
                .build();
        GetCallerIdentityRequest request = new GetCallerIdentityRequest();
        GetCallerIdentityResult response = stsClient.getCallerIdentity(request);
        System.out.println(response.getArn());

    }

}

