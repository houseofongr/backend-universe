package com.hoo.universe.api.in.space;

import com.hoo.common.internal.api.dto.UploadFileRequest;
import com.hoo.universe.api.dto.result.space.OverwriteSpaceFileResult;

import java.util.UUID;

public interface OverwriteSpaceFileUseCase {
    OverwriteSpaceFileResult overwriteSpaceFile(UUID universeID, UUID spaceID, UploadFileRequest background);
}
