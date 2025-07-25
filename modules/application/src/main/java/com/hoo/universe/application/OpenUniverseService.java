package com.hoo.universe.application;

import com.hoo.universe.api.in.OpenUniverseUseCase;
import com.hoo.universe.api.in.dto.OpenUniverseResult;
import com.hoo.universe.api.out.FileUrlResolveInCase;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.domain.Universe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OpenUniverseService implements OpenUniverseUseCase {

    private final LoadUniversePort loadUniversePort;
    private final FileUrlResolveInCase fileUrlResolveInCase;
    private final FileOwnerMapExtractor fileOwnerMapExtractor;
    private final ApplicationMapper applicationMapper;

    @Override
    public OpenUniverseResult openUniverseWithComponents(UUID universeID) {
        Universe universe = loadUniversePort.loadUniverseExceptSounds(universeID);
        Map<UUID, UUID> fileOwnerMap = fileOwnerMapExtractor.extractFileOwnerMap(universe);
        Map<UUID, URI> uuidUriMap = fileUrlResolveInCase.resolveBatch(fileOwnerMap);
        return applicationMapper.mapToOpenUniverseResult(universe, uuidUriMap);
    }
}
