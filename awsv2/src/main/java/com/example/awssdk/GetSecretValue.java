package com.example.awssdk;

import com.example.awssdk.settings.SecretsSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

import javax.annotation.PostConstruct;

/**
 * To run this AWS code example, ensure that you have setup your development environment, including your AWS credentials.
 *
 * For information, see this documentation topic:
 *
 *https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */

@Component
public class GetSecretValue {

    @Autowired
    private SecretsSettings secrets;

    @PostConstruct
    public void init() {

//        Region region = Region.US_EAST_1;
//
//        String roleArn = "arn:aws:iam::044712912013:role/s3-role";
//        String roleSessionName = "sessionName";
//
//        final AssumeRoleRequest assumeRoleRequest = AssumeRoleRequest.builder().roleSessionName(roleSessionName).roleArn(roleArn).build();
//        final StsClient stsClient = StsClient.builder().region(Region.US_EAST_1).build();
//        final StsAssumeRoleCredentialsProvider stsAssumeRoleCredentialsProvider = StsAssumeRoleCredentialsProvider.builder().stsClient(stsClient).refreshRequest(assumeRoleRequest).build();



        SecretsManagerClient secretsClient = SecretsManagerClient.builder()
//                .credentialsProvider(stsAssumeRoleCredentialsProvider)
                .region(Region.US_EAST_1)
                .build();

        secrets.setIsrael(getValue(secretsClient, "dev/user/israel"));
        secrets.setFuncionaltest(getValue(secretsClient, "dev/funcional-test"));

        secretsClient.close();
    }

    public static String getValue(SecretsManagerClient secretsClient,String secretName) {

        try {
            GetSecretValueRequest valueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

            GetSecretValueResponse valueResponse = secretsClient.getSecretValue(valueRequest);
            String secret = valueResponse.secretString();
            System.out.println(secret);

            return secret;

        } catch (SecretsManagerException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }

        return null;
    }
}
