package com.hoo.universe.api.in.dto;

import com.hoo.common.internal.api.file.dto.UploadFileCommand;

import java.util.UUID;

public record CreateSpaceWithTwoPointCommand(
        Metadata metadata,
        UploadFileCommand.FileSource background
) {
    public CreateSpaceWithTwoPointCommand {
        if (background == null)
            throw new IllegalArgumentException();
    }

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
