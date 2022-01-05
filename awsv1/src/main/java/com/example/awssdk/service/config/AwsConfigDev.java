package com.example.awssdk.service.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Profile("dev")
public class AwsConfigDev extends AwsConfigAbstract {

    @PostConstruct
    public void init() {
        renew();
    }

    @Override
    public AWSCredentialsProvider getProvider() {
        return new DefaultAWSCredentialsProviderChain();
    }

}

