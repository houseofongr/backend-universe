package com.hoo.admin.application.port.out.universe;

import com.hoo.admin.application.port.in.universe.UpdateUniverseResult;
import com.hoo.admin.domain.universe.Universe;

public interface UpdateUniversePort {
    UpdateUniverseResult.Detail updateDetail(Universe universe);

    void updateThumbMusic(Universe universe);

    void updateThumbnail(Universe universe);

    void updateInnerImage(Universe universe);
}
