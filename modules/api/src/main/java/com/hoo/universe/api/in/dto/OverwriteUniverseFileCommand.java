package com.hoo.universe.api.in.dto;


import com.hoo.common.internal.api.dto.FileCommand;

public record OverwriteUniverseFileCommand(
        FileCommand thumbmusic,
        FileCommand thumbnail,
        FileCommand background
) {
}
