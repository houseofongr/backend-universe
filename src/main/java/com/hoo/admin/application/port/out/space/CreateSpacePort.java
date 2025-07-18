package com.hoo.admin.application.port.out.space;

import com.hoo.admin.application.port.in.space.CreateSpaceCommand;
import com.hoo.admin.domain.universe.space.Space;

public interface CreateSpacePort {
    Space createSpace(CreateSpaceCommand command, Long innerImageFileId);
}
