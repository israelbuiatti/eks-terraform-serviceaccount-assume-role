package com.example.awssdk.settings;

import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("aws-credential")
@Data
public class AwsCredentialsSettings {

    private String accessKeyId;
    private String secretAccessKey;
    private String sessionToken;
    private String region;
    private BasicSessionCredentials awsCredentials;
    private AmazonS3 s3Client;

    @Value("${AWS_ROLE_ARN}")
    private String roleArn;

    @Value("${AWS_WEB_IDENTITY_TOKEN_FILE}")
    private String serviceAccountTokenFile;

    @Value("${AWS_DEFAULT_REGION}")
    private String clientRegion;

}
