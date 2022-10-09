package com.gavrysh.demo.stream16;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@RequiredArgsConstructor
public class AwsS3Configuration {

    private final AppProperties appProperties;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(appProperties.getS3Region())
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }
}
