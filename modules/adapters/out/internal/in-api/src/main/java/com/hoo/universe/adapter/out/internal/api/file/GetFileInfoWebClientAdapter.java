package com.hoo.universe.adapter.out.internal.api.file;

import com.hoo.common.internal.api.GetFileInfoAPI;
import com.hoo.common.internal.api.dto.FileInfo;
import com.hoo.universe.adapter.out.internal.api.InternalAPIConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class GetFileInfoWebClientAdapter implements GetFileInfoAPI {

    private final WebClient webClient;
    private final InternalAPIConfigProperties internalAPIConfigProperties;

    @Override
    public FileInfo getFileInfo(UUID fileID) {

        return webClient.get()
                .uri(String.format(internalAPIConfigProperties.getFile().getUploadFileUrl(), fileID))
                .retrieve()
                .bodyToMono(FileInfo.class)
                .block();
    }

    @Override
    public List<FileInfo> getFileInfo(UUID... fileID) {
        return List.of();
    }

    @Override
    public List<FileInfo> getFileInfo(Collection<UUID> fileIDs) {
        return List.of();
    }
}
