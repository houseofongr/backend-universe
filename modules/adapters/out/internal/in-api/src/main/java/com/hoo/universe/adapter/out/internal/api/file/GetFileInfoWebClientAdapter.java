package com.hoo.universe.adapter.out.internal.api.file;

import com.hoo.common.internal.api.file.GetFileInfoAPI;
import com.hoo.common.internal.api.file.dto.FileInfo;
import com.hoo.common.internal.api.file.dto.GetFileInfoCommand;
import com.hoo.universe.adapter.out.internal.api.InternalProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RequiredArgsConstructor
public class GetFileInfoWebClientAdapter implements GetFileInfoAPI {

    private final WebClient webClient;
    private final InternalProperties internalProperties;

    @Override
    public List<FileInfo> getFileInfo(GetFileInfoCommand command) {
        return webClient.post()
                .uri(String.format(internalProperties.getFile().getGetFileInfoUrl()))
                .header("Content-Type", "application/json")
                .bodyValue(command)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<FileInfo>>() {
                })
                .block();
    }
}
