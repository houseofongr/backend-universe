package com.hoo.universe.api.dto.command.piece;

import com.hoo.common.internal.api.dto.FileCommand;

import java.util.UUID;

public record CreatePieceWithTwoPointCommand(
        Metadata metadata,
        FileCommand background
) {

    public record Metadata(
            UUID parentSpaceID,
            String title,
            String description,
            Double startX,
            Double startY,
            Double endX,
            Double endY,
            Boolean hidden
    ) {
        public Metadata {
            if ((title == null || title.isBlank() || title.length() > 100) ||
                (description == null || description.length() > 5000) ||
                (startX == null || startX < 0 || startX > 1) ||
                (startY == null || startY < 0 || startY > 1) ||
                (endX == null || endX < 0 || endX > 1) ||
                (endY == null || endY < 0 || endY > 1) ||
                (hidden == null)
            )
                throw new IllegalArgumentException();
        }
    }
}
