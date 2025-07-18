package com.hoo.universe.api.dto.command;

import java.util.List;
import java.util.UUID;

public record UpdateUniverseMetadataCommand(
        String title,
        String description,
        UUID authorID,
        UUID categoryID,
        String accessStatus,
        List<String> hashtags
) {

    public UpdateUniverseMetadataCommand {
        if ((title != null && (title.isBlank() || title.length() > 100) ||
             (description != null && description.length() > 5000) ||
             (hashtags != null && !hashtags.isEmpty()) && (hashtags.size() > 10 || String.join("", hashtags).length() > 500)))

            throw new IllegalArgumentException();
    }

}
