package com.hoo.universe.adapter.out.internal.api;

import com.hoo.universe.adapter.out.internal.api.file.UploadFileWebClientAdapter;
import com.hoo.universe.adapter.out.internal.api.user.FindOwnerWebClientAdapter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(InternalAPIConfigProperties.class)
public class InternalAPIConfig {

    @Bean
    public UploadFileWebClientAdapter fileUploadAPIAdapter(
            WebClient fileAPIWebClient,
            InternalAPIConfigProperties internalAPIConfigProperties
    ) {

        return new UploadFileWebClientAdapter(fileAPIWebClient, internalAPIConfigProperties);
    }

    @Bean
    public FindOwnerWebClientAdapter findUserAPIAdapter(
            WebClient userAPIWebClient,
            InternalAPIConfigProperties internalAPIConfigProperties) {

        return new FindOwnerWebClientAdapter(userAPIWebClient, internalAPIConfigProperties);
    }

    @Bean
    public WebClient userAPIWebClient(InternalAPIConfigProperties internalAPIConfigProperties) {

        return WebClient.builder()
                .baseUrl(internalAPIConfigProperties.getUser().getBaseUrl())
                .build();
    }

    @Bean
    public WebClient fileAPIWebClient(InternalAPIConfigProperties internalAPIConfigProperties) {

        return WebClient.builder()
                .baseUrl(internalAPIConfigProperties.getUser().getBaseUrl())
                .build();
    }
}
