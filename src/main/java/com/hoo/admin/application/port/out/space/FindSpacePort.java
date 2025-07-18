package com.hoo.admin.application.port.out.space;

import com.hoo.admin.domain.universe.space.Space;

public interface FindSpacePort {
    Space find(Long id);
    Long findUniverseId(Long id);
}
