package com.hoo.universe.api.in.web.dto.command;

public record UpdateSoundMetadataCommand(
        String title,
        String description,
        Boolean hidden
) {
    public UpdateSoundMetadataCommand {
        if ((title != null && (title.isBlank() || title.length() > 100) ||
             (description != null && description.length() > 5000)
        ))
            throw new IllegalArgumentException();
    }
}
