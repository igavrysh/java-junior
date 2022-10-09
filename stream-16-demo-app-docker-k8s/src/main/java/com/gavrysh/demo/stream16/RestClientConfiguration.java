package com.gavrysh.demo.stream16;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
@RequiredArgsConstructor
public class RestClientConfiguration {

    private final AppProperties appProperties;

    @Bean
    public RestTemplate restTemplate() {
        final var template = new RestTemplate(getRequestFactory());
        template.setUriTemplateHandler(new DefaultUriBuilderFactory(appProperties.getBaseUrl()));
        return template;
    }

    private SimpleClientHttpRequestFactory getRequestFactory() {
        final var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(appProperties.getConnectTimeout());
        factory.setReadTimeout(appProperties.getReadTimeout());
        return factory;
    }
}
