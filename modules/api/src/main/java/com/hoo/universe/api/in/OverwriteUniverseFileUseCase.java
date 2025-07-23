package com.hoo.universe.api.in;

import com.hoo.common.internal.api.file.dto.UploadFileCommand;
import com.hoo.universe.api.in.dto.OverwriteUniverseFileResult;

import java.util.UUID;

public interface OverwriteUniverseFileUseCase {
    OverwriteUniverseFileResult.Thumbmusic overwriteUniverseThumbmusic(UUID universeID, UploadFileCommand.FileSource thumbmusic);

    OverwriteUniverseFileResult.Thumbnail overwriteUniverseThumbnail(UUID universeID, UploadFileCommand.FileSource thumbnail);

    OverwriteUniverseFileResult.Background overwriteUniverseBackground(UUID universeID, UploadFileCommand.FileSource background);
}
