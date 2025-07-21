package com.hoo.universe.api.in;

import com.hoo.common.internal.api.dto.FileCommand;
import com.hoo.universe.api.in.dto.OverwriteSpaceFileResult;

import java.util.UUID;

public interface OverwriteSpaceFileUseCase {
    OverwriteSpaceFileResult overwriteSpaceFile(UUID universeID, UUID spaceID, FileCommand background);
}
