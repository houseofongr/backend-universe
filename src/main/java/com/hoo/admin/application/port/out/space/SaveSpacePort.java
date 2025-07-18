package com.hoo.admin.application.port.out.space;

import com.hoo.admin.application.port.in.space.CreateSpaceResult;
import com.hoo.admin.domain.universe.space.Space;

public interface SaveSpacePort {
    CreateSpaceResult save(Space space);
}
