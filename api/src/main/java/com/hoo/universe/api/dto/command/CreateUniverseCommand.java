package com.hoo.universe.api.dto.command;



import com.hoo.common.internal.api.dto.UploadFileRequest;

import java.util.List;
import java.util.UUID;

public record CreateUniverseCommand(
        Metadata metadata,
        UploadFileRequest thumbMusic,
        UploadFileRequest thumbnail,
        UploadFileRequest background
) {

    public CreateUniverseCommand {
        if (thumbMusic == null ||
            thumbnail == null ||
            background == null)
            throw new IllegalArgumentException();
    }

    public record Metadata(
            String title,
            String description,
            UUID authorID,
            UUID categoryID,
            String accessStatus,
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
