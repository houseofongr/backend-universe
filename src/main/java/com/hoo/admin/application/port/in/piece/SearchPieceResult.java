package com.hoo.admin.application.port.in.piece;

import com.hoo.common.application.port.in.Pagination;

import java.util.List;

public record SearchPieceResult(
        Long pieceId,
        String title,
        String description,
        Boolean hidden,
        Long createdTime,
        Long updatedTime,
        List<SoundInfo> sounds,
        Pagination pagination
) {
    public record SoundInfo(
            Long soundId,
            Long audioId,
            String title,
            String description,
            Boolean hidden,
            Long createdTime,
            Long updatedTime
    ) {

    }
}
