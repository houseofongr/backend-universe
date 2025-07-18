package com.hoo.admin.application.port.out.universe;

import com.hoo.admin.application.port.in.universe.CreateUniverseCommand;
import com.hoo.admin.domain.universe.Universe;

public interface CreateUniversePort {
    Universe createUniverse(CreateUniverseCommand command, Long thumbMusicId, Long thumbnailId, Long innerImageId);
}
