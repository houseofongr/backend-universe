package com.hoo.universe.api.in.dto;

public record UpdatePieceMetadataCommand(
        String title,
        String description,
        Boolean hidden
) {
    public UpdatePieceMetadataCommand {
        if ((title != null && (title.isBlank() || title.length() > 100)) ||
            (description != null && description.length() > 5000))
            throw new IllegalArgumentException();
    }
}
