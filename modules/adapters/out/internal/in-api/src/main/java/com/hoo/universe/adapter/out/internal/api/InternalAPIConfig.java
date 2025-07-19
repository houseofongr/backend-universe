package com.hoo.universe.adapter.out.internal.api;

import com.hoo.universe.adapter.out.internal.api.file.GetFileInfoWebClientAdapter;
import com.hoo.universe.adapter.out.internal.api.file.UploadFileWebClientAdapter;
import com.hoo.universe.adapter.out.internal.api.user.GetUserInfoWebClientAdapter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(InternalAPIConfigProperties.class)
public class InternalAPIConfig {

    @Bean
    public GetFileInfoWebClientAdapter getFileInfoAPIAdapter(
            WebClient fileAPIWebClient,
            InternalAPIConfigProperties internalAPIConfigProperties
    ) {

        return new GetFileInfoWebClientAdapter(fileAPIWebClient, internalAPIConfigProperties);
    }

    @Bean
    public UploadFileWebClientAdapter uploadFileAPIAdapter(
            WebClient fileAPIWebClient,
            InternalAPIConfigProperties internalAPIConfigProperties
    ) {

        return new UploadFileWebClientAdapter(fileAPIWebClient, internalAPIConfigProperties);
    }

    @Bean
    public GetUserInfoWebClientAdapter getOwnerAPIAdapter(
            WebClient userAPIWebClient,
            InternalAPIConfigProperties internalAPIConfigProperties) {

        return new GetUserInfoWebClientAdapter(userAPIWebClient, internalAPIConfigProperties);
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
