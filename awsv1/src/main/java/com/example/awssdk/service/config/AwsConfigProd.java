package com.example.awssdk.service.config;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.*;
import com.example.awssdk.settings.AwsCredentialsSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;

@Component
@Profile("prod")
public class AwsConfigProd extends AwsConfigAbstract {

    @Autowired
    private AwsCredentialsSettings awsCredentialsSettings;

    @PostConstruct
    public void init() {
        renew();
    }

    @PostConstruct
    public AWSCredentialsProvider getProvider() {

        String roleSessionName = "sessionName";
        int duration = 900; //The duration must be between 3,600 (1 hour) and 43,200 (12 hours)


        System.out.println(".");
        System.out.println(".");
        System.out.println(".");

        System.out.println("AWS_ROLE_ARN= " + awsCredentialsSettings.getRoleArn());
        System.out.println("AWS_WEB_IDENTITY_TOKEN_FILE= " + awsCredentialsSettings.getServiceAccountTokenFile());
        System.out.println("AWS_DEFAULT_REGION= " + awsCredentialsSettings.getRegion());

        System.out.println(".");
        System.out.println(".");
        System.out.println(".");


        try {

            //ASSUME ROLE
            AWSSecurityTokenService client = AWSSecurityTokenServiceClientBuilder.standard().build();
            AssumeRoleWithWebIdentityRequest request = new AssumeRoleWithWebIdentityRequest()
                    .withRoleArn(awsCredentialsSettings.getRoleArn())
                    .withRoleSessionName(roleSessionName)
                    .withWebIdentityToken(getWebIdentityToken(awsCredentialsSettings.getServiceAccountTokenFile())).withDurationSeconds(duration);
            AssumeRoleWithWebIdentityResult response = client.assumeRoleWithWebIdentity(request);

            Credentials sessionCredentials = response.getCredentials();

            BasicSessionCredentials awsCredentials = new BasicSessionCredentials(
                    sessionCredentials.getAccessKeyId(),
                    sessionCredentials.getSecretAccessKey(),
                    sessionCredentials.getSessionToken());

            return new AWSStaticCredentialsProvider(awsCredentials);

        }
        catch(AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        }
        catch(SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();

        }

        return null;

    }

    private String getWebIdentityToken(String webIdentityTokenFile) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(webIdentityTokenFile), "UTF-8"));
            return br.readLine();
        } catch (FileNotFoundException e) {
            throw new SdkClientException("Unable to locate specified web identity token file: " + webIdentityTokenFile);
        } catch (IOException e) {
            throw new SdkClientException("Unable to read web identity token from file: " + webIdentityTokenFile);
        } finally {
            try {
                br.close();
            } catch (Exception ignored) {

            }
        }
    }

}

