package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.internal.message.DeleteFileEventPublisher;
import com.hoo.universe.api.out.DeleteEntityPort;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverse;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class DeleteSoundServiceTest {

    LoadUniversePort loadUniversePort = mock();
    DeleteEntityPort deleteEntityPort = mock();
    DeleteFileEventPublisher deleteFileEventPublisher = mock();

    DeleteSoundService sut = new DeleteSoundService(loadUniversePort, deleteEntityPort, deleteFileEventPublisher);

    @Test
    @DisplayName("사운드 삭제 서비스")
    void deleteSoundService() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        Universe universe = defaultUniverse();
        UUID pieceID = universe.getPieces().getFirst().getId().uuid();
        UUID soundID = universe.getPieces().getFirst().getSounds().getFirst().getId().uuid();

        // when
        when(loadUniversePort.loadUniverseWithAllEntity(universeID)).thenReturn(universe);
        sut.deleteSound(universeID, pieceID, soundID);

        // then
        verify(deleteEntityPort, times(1)).deleteSound(any());
        verify(deleteFileEventPublisher, times(1)).publishDeleteFilesEvent((UUID) any());
    }

}