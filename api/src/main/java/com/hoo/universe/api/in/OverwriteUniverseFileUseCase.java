package com.hoo.universe.api.in;

import com.hoo.common.internal.api.dto.UploadFileRequest;
import com.hoo.universe.api.dto.result.OverwriteUniverseFileResult;

import java.util.UUID;

public interface OverwriteUniverseFileUseCase {
    OverwriteUniverseFileResult.Thumbmusic overwriteUniverseThumbmusic(UUID universeID, UploadFileRequest thumbmusic);

    OverwriteUniverseFileResult.Thumbnail overwriteUniverseThumbnail(UUID universeID, UploadFileRequest thumbnail);

    OverwriteUniverseFileResult.Background overwriteUniverseBackground(UUID universeID, UploadFileRequest background);
}
