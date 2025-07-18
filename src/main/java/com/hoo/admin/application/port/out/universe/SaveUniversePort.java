package com.hoo.admin.application.port.out.universe;

import com.hoo.admin.domain.universe.Universe;

public interface SaveUniversePort {
    Universe save(Universe universe);
}
