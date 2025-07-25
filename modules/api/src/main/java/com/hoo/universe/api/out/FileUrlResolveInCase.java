package com.hoo.universe.api.out;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

public interface FileUrlResolveInCase {
    Map<UUID, URI> resolveBatch(Map<UUID, UUID> fileOwnerMap);
}
