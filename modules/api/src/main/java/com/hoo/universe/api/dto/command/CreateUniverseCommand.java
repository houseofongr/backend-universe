package com.hoo.universe.api.dto.command;



import com.hoo.common.internal.api.dto.FileCommand;

import java.util.List;
import java.util.UUID;

public record CreateUniverseCommand(
        Metadata metadata,
        FileCommand thumbmusic,
        FileCommand thumbnail,
        FileCommand background
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
