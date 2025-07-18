package com.hoo.universe.api.dto.command.sound;

import com.hoo.common.internal.api.dto.UploadFileRequest;

public record CreateSoundCommand(
        Metadata metadata,
        UploadFileRequest audio
) {
    public CreateSoundCommand {
        if (audio == null) throw new IllegalArgumentException();
    }

    public record Metadata(
            String title,
            String description,
            Boolean hidden
    ) {

        public Metadata {
            if ((title == null || title.isBlank() || title.length() > 100) ||
                (description != null && description.length() > 5000) ||
                (hidden == null)
            )
                throw new IllegalArgumentException();
        }
    }
}
