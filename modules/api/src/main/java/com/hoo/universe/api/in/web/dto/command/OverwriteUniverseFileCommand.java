package com.hoo.universe.api.in.web.dto.command;


import com.hoo.common.internal.api.dto.FileCommand;

public record OverwriteUniverseFileCommand(
        FileCommand thumbmusic,
        FileCommand thumbnail,
        FileCommand background
) {
}
