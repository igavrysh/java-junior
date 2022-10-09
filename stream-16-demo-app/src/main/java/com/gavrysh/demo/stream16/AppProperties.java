package com.gavrysh.demo.stream16;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import lombok.Data;
import software.amazon.awssdk.regions.Region;

@Data
@ConstructorBinding
@ConfigurationProperties("app")
public class AppProperties {

    private final int connectTimeout;

    private final int readTimeout;

    private final String baseUrl;

    private final String s3Bucket;

    private final Region s3Region;

}
