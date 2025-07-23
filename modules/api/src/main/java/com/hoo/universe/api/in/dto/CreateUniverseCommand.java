package com.hoo.universe.api.in.dto;

import com.hoo.common.internal.api.file.dto.UploadFileCommand;

import java.util.List;
import java.util.UUID;

public record CreateUniverseCommand(
        Metadata metadata,
        UploadFileCommand.FileSource thumbmusic,
        UploadFileCommand.FileSource thumbnail,
        UploadFileCommand.FileSource background
) {

    public CreateUniverseCommand {
        if (thumbmusic == null ||
            thumbnail == null ||
            background == null)
            throw new IllegalArgumentException();
    }

    public record Metadata(
            String title,
            String description,
            UUID ownerID,
            UUID categoryID,
            String accessLevel,
            List<String> hashtags
    ) {
        public Metadata {
            if ((title == null || title.isBlank() || title.length() > 100) ||
                (description == null || description.length() > 5000) ||
                (hashtags.size() > 10 || String.join("", hashtags).length() > 500)
            )
                throw new IllegalArgumentException();
        }
    }

}
