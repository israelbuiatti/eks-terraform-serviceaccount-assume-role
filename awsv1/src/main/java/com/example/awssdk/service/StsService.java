package com.example.awssdk.service;

import com.amazonaws.services.securitytoken.model.GetCallerIdentityRequest;
import com.amazonaws.services.securitytoken.model.GetCallerIdentityResult;
import com.example.awssdk.settings.AwsCredentialsSettings;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Log4j2
public class StsService {

    @Autowired
    private AwsCredentialsSettings awsCredentialsSettings;

    public String getCallerIdentity()  {

        GetCallerIdentityRequest request = new GetCallerIdentityRequest();
        GetCallerIdentityResult response = awsCredentialsSettings.getStsClient().getCallerIdentity(request);

        return response.getArn();

    }

}
