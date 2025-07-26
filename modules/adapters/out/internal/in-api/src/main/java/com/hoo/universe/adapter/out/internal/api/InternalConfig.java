package com.hoo.universe.adapter.out.internal.api;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

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
                .filter(errorHandlingFilter())
                .build();
    }

    @Bean
    public WebClient fileWebClient(InternalProperties internalProperties) {
        return WebClient.builder()
                .baseUrl(internalProperties.getFile().getBaseUrl())
                .filter(errorHandlingFilter())
                .build();
    }

    private ExchangeFilterFunction errorHandlingFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().isError()) {
                return clientResponse.bodyToMono(String.class).flatMap(body ->
                        Mono.error(new WebClientResponseException(
                                clientResponse.statusCode().value(),
                                clientResponse.statusCode().toString(),
                                clientResponse.headers().asHttpHeaders(),
                                body.getBytes(StandardCharsets.UTF_8),
                                StandardCharsets.UTF_8
                        ))
                );
            }
            return Mono.just(clientResponse);
        });
    }

}
