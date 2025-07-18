package com.hoo.universe.application;

import com.hoo.universe.api.dto.result.OpenUniverseResult;
import com.hoo.universe.api.in.OpenUniverseUseCase;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OpenUniverseService implements OpenUniverseUseCase {

    private final LoadUniversePort loadUniversePort;

    @Override
    public OpenUniverseResult openUniverseWithComponents(UUID universeID) {

        return OpenUniverseResult.from(loadUniversePort.loadUniverseExceptSounds(universeID));
    }
}
