package com.example.awssdk.settings;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties("secrets")
@Data
public class SecretsSettings {

    private String israel;
    private String funcionaltest;
    private List listBucket;
}
