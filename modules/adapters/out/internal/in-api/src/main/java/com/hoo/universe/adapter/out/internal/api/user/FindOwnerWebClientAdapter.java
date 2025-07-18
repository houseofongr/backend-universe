package com.hoo.universe.adapter.out.internal.api.user;

import com.hoo.universe.adapter.out.internal.api.InternalAPIConfigProperties;
import com.hoo.universe.api.out.internal.FindOwnerAPI;
import com.hoo.universe.domain.vo.Owner;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@RequiredArgsConstructor
public class FindOwnerWebClientAdapter implements FindOwnerAPI {

    private final WebClient webClient;
    private final InternalAPIConfigProperties internalAPIConfigProperties;

    @Override
    public Owner findOwner(UUID ownerID) {

        String nickname = webClient.get()
                .uri(internalAPIConfigProperties.getUser().getFindUserNicknameUrl() + ownerID)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return new Owner(ownerID, nickname);
    }
}
