package com.hoo.universe.api.in.dto;

import com.hoo.common.internal.api.dto.FileCommand;

import java.util.UUID;

public record OverwriteSoundFileCommand(
        UUID soundID, FileCommand audioFileCommand
) {
}
