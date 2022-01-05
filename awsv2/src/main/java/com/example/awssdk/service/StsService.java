package com.example.awssdk.service;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.GetCallerIdentityResponse;

@Component
public class StsService {

    public String getCallerIdentity()  {

        Region region = Region.US_EAST_1;
        StsClient stsClient = StsClient.builder()
                .region(region)
                .build();

        GetCallerIdentityResponse response = stsClient.getCallerIdentity();

        stsClient.close();

        return response.arn();

    }

}
