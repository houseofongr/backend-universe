package com.hoo.universe.api.in.web.usecase;

import com.hoo.common.internal.api.dto.FileCommand;
import com.hoo.universe.api.in.web.dto.result.OverwriteSpaceFileResult;

import java.util.UUID;

public interface OverwriteSpaceFileUseCase {
    OverwriteSpaceFileResult overwriteSpaceFile(UUID universeID, UUID spaceID, FileCommand background);
}
