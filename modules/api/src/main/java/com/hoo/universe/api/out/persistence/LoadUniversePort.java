package com.hoo.universe.api.out.persistence;

import com.hoo.universe.domain.Universe;

import java.util.UUID;

public interface LoadUniversePort {

    Universe loadUniverseOnly(UUID universeID);

    Universe loadUniverseExceptSounds(UUID universeID);

    Universe loadUniverseWithAllEntity(UUID universeID);
}
