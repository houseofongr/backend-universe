package com.hoo.admin.application.port.in.soundsource;

import com.hoo.common.application.port.in.Pagination;

import java.util.List;

public record QuerySoundSourceListResult(
        List<SoundSourceInfo> soundSources,
        Pagination pagination
) {
    public record SoundSourceInfo(
            String name,
            String description,
            String createdDate,
            String updatedDate,
            Boolean isActive,
            Long audioFileId,
            String userNickname,
            String userEmail,
            Long userId,
            String homeName,
            Long homeId,
            String roomName,
            Long roomId,
            String itemName,
            Long itemId
    ) {

    }
}
