package com.hoo.universe.api.in.web.dto.command;

import java.util.List;
import java.util.UUID;

public record UpdateUniverseMetadataCommand(
        String title,
        String description,
        UUID ownerID,
        UUID categoryID,
        String accessLevel,
        List<String> hashtags
) {

    public UpdateUniverseMetadataCommand {
        if ((title != null && (title.isBlank() || title.length() > 100) ||
             (description != null && description.length() > 5000) ||
             (hashtags != null && !hashtags.isEmpty()) && (hashtags.size() > 10 || String.join("", hashtags).length() > 500)))

            throw new IllegalArgumentException();
    }

}
