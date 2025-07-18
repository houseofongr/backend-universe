package com.hoo.aar.application.port.in.home;

import com.hoo.common.application.port.in.Pagination;

import java.util.List;

public record QuerySoundSourcesPathResult(
        List<SoundSourcePathInfo> soundSources,
        Pagination pagination
) {
    public record SoundSourcePathInfo(
            String name,
            String description,
            String createdDate,
            String updatedDate,
            Long audioFileId,
            String homeName,
            Long homeId,
            String roomName,
            Long roomId,
            String itemName,
            Long itemId
    ) {

    }
}
