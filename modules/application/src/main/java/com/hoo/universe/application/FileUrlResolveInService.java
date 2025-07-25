package com.hoo.universe.application;

import com.hoo.common.internal.api.file.GetFileInfoAPI;
import com.hoo.common.internal.api.file.dto.FileInfo;
import com.hoo.common.internal.api.file.dto.GetFileInfoCommand;
import com.hoo.universe.api.out.FileUrlResolveInCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.*;

@Component
@RequiredArgsConstructor
public class FileUrlResolveInService implements FileUrlResolveInCase {

    private final GetFileInfoAPI getFileInfoAPI;

    @Override
    public Map<UUID, URI> resolveBatch(Map<UUID, UUID> fileOwnerMap) {

        Map<UUID, URI> result = new HashMap<>();

        GetFileInfoCommand command = new GetFileInfoCommand(fileOwnerMap.entrySet().stream()
                .map(entry -> new GetFileInfoCommand.FileOwnership(entry.getKey(), entry.getValue()))
                .toList());

        for (FileInfo fileInfo : getFileInfoAPI.getFileInfo(command)) {
            result.put(fileInfo.id(), fileInfo.fileUrl());
        };

        return result;
    }

}
