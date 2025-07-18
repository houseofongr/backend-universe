package com.hoo.universe.application;

import com.hoo.common.internal.api.GetFileInfoAPI;
import com.hoo.common.internal.api.dto.FileInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.*;

@Component
@RequiredArgsConstructor
public class FileUrlResolver {

    private final GetFileInfoAPI getFileInfoAPI;

    public URI resolve(UUID fileID) {

        return getFileInfoAPI.getFileInfo(fileID).fileUrl();
    }

    public Map<UUID, URI> resolveBatch(Collection<UUID> fileIDs) {

        Map<UUID, URI> result = new HashMap<>();

        for (FileInfo fileInfo : getFileInfoAPI.getFileInfo(fileIDs)) {
            result.put(fileInfo.id(), fileInfo.fileUrl());
        };

        return result;
    }

    public Map<UUID, URI> resolveBatch(UUID... fileID) {

        return resolveBatch(Arrays.stream(fileID).toList());
    }
}
