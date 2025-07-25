package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.in.dto.UpdateSoundMetadataCommand;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.api.out.UpdateSoundStatusPort;
import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.*;
import static org.mockito.Mockito.*;

class UpdateSoundMetadataServiceTest {

    LoadUniversePort loadUniversePort = mock();
    UpdateSoundStatusPort updateSoundStatusPort = mock();

    UpdateSoundMetadataService sut = new UpdateSoundMetadataService(loadUniversePort, updateSoundStatusPort);

    @Test
    @DisplayName("사운드 상세정보 수정 서비스")
    void updateSoundMetadataService() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        UpdateSoundMetadataCommand command = new UpdateSoundMetadataCommand("수정", "수정수정", true);
        Universe universe = defaultUniverse();
        UUID pieceID = universe.getPieces().getFirst().getId().uuid();
        UUID soundID = universe.getPieces().getFirst().getSounds().getFirst().getId().uuid();

        // when
        when(loadUniversePort.loadUniverseWithAllEntity(universeID)).thenReturn(universe);
        sut.updateSoundMetadata(universeID, pieceID, soundID, command);

        // then
        verify(updateSoundStatusPort, times(1)).updateSoundMetadata(any());
    }

}