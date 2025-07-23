package com.hoo.universe.api.in;

import com.hoo.common.internal.api.file.dto.UploadFileCommand;
import com.hoo.universe.api.in.dto.OverwriteSpaceFileResult;

import java.util.UUID;

public interface OverwriteSpaceFileUseCase {
    OverwriteSpaceFileResult overwriteSpaceFile(UUID universeID, UUID spaceID, UploadFileCommand.FileSource background);
}
