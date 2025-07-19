package com.hoo.universe.application.piece;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.in.web.dto.command.UpdatePieceMetadataCommand;
import com.hoo.universe.api.out.persistence.HandlePieceEventPort;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverse;
import static org.mockito.Mockito.*;

class UpdatePieceMetadataServiceTest {

    LoadUniversePort loadUniversePort = mock();
    HandlePieceEventPort handlePieceEventPort = mock();

    UpdatePieceMetadataService sut = new UpdatePieceMetadataService(loadUniversePort, handlePieceEventPort);

    @Test
    @DisplayName("피스 상세정보 수정 서비스")
    void updatePieceMetadataService() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        UpdatePieceMetadataCommand command = new UpdatePieceMetadataCommand("수정", "수정수정", true);
        Universe universe = defaultUniverse();
        UUID pieceID = universe.getPieces().getFirst().getId().uuid();

        // when
        when(loadUniversePort.loadUniverseExceptSounds(universeID)).thenReturn(universe);
        sut.updatePieceMetadata(universeID, pieceID, command);

        // then
        verify(handlePieceEventPort, times(1)).handlePieceMetadataUpdateEvent(any());
    }

}