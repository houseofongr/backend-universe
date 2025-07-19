package com.hoo.universe.api.out.internal;

import com.hoo.universe.domain.vo.Owner;

import java.util.UUID;

public interface GetOwnerAPI {
    Owner getOwner(UUID ownerID);
}
