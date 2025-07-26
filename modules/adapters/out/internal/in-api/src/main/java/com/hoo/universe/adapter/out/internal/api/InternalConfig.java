package com.hoo.universe.adapter.out.internal.api;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(InternalProperties.class)
public class InternalConfig {

    @Bean
    public FileWebClientAdapter fileWebClientAdapter(
            WebClient fileWebClient,
            InternalProperties internalProperties
    ) {

        return new FileWebClientAdapter(fileWebClient, internalProperties);
    }

    @Bean
    public UserWebClientAdapter userWebClientAdapter(
            WebClient userWebClient,
            InternalProperties internalProperties) {

        return new UserWebClientAdapter(userWebClient, internalProperties);
    }

    @Bean
    public WebClient userWebClient(InternalProperties internalProperties) {

        return WebClient.builder()
                .baseUrl(internalProperties.getUser().getBaseUrl())
                .build();
    }

    @Bean
    public WebClient fileWebClient(InternalProperties internalProperties) {

        return WebClient.builder()
                .baseUrl(internalProperties.getFile().getBaseUrl())
                .build();
    }
}
