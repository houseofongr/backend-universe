package com.hoo.universe.application.space;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.in.web.dto.command.UpdateSpaceMetadataCommand;
import com.hoo.universe.api.out.persistence.HandleSpaceEventPort;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.application.UpdateSpaceMetadataService;
import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverse;
import static org.mockito.Mockito.*;

class UpdateSpaceMetadataServiceTest {

    LoadUniversePort loadUniversePort = mock();
    HandleSpaceEventPort handleSpaceEventPort = mock();

    UpdateSpaceMetadataService sut = new UpdateSpaceMetadataService(loadUniversePort, handleSpaceEventPort);

    @Test
    @DisplayName("스페이스 상세정보 수정 서비스")
    void updateSpaceMetadataService() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        UpdateSpaceMetadataCommand command = new UpdateSpaceMetadataCommand("수정", "수정수정", true);
        Universe universe = defaultUniverse();
        UUID spaceID = universe.getSpaces().getFirst().getId().uuid();

        // when
        when(loadUniversePort.loadUniverseExceptSounds(universeID)).thenReturn(universe);
        sut.updateSpaceMetadata(universeID, spaceID, command);

        // then
        verify(handleSpaceEventPort, times(1)).handleSpaceMetadataUpdateEvent(any());
    }
}