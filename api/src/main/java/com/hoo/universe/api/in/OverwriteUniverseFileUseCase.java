package com.hoo.universe.api.in;

import com.hoo.common.internal.api.dto.FileCommand;
import com.hoo.universe.api.dto.result.OverwriteUniverseFileResult;

import java.util.UUID;

public interface OverwriteUniverseFileUseCase {
    OverwriteUniverseFileResult.Thumbmusic overwriteUniverseThumbmusic(UUID universeID, FileCommand thumbmusic);

    OverwriteUniverseFileResult.Thumbnail overwriteUniverseThumbnail(UUID universeID, FileCommand thumbnail);

    OverwriteUniverseFileResult.Background overwriteUniverseBackground(UUID universeID, FileCommand background);
}
