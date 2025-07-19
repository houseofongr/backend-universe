package com.hoo.universe.api.in.web.dto.command;

import com.hoo.common.internal.api.dto.FileCommand;

import java.util.UUID;

public record OverwriteSoundFileCommand(
        UUID soundID, FileCommand audioFileCommand
) {
}
