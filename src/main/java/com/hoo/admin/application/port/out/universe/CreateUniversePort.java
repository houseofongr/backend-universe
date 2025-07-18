package com.hoo.admin.application.port.out.universe;

import com.hoo.admin.application.port.in.universe.CreateUniverseCommand;
import com.hoo.admin.domain.universe.Universe;

public interface CreateUniversePort {
    Universe createUniverse(CreateUniverseCommand command, Long thumbmusicId, Long thumbnailId, Long innerImageId);
}
