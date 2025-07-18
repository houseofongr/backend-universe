package com.hoo.universe.application.sound;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.internal.message.FileDeleteEventPublisher;
import com.hoo.universe.api.out.persistence.HandleSoundEventPort;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverse;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class DeleteSoundServiceTest {

    LoadUniversePort loadUniversePort = mock();
    HandleSoundEventPort handleSoundEventPort = mock();
    FileDeleteEventPublisher fileDeleteEventPublisher = mock();

    DeleteSoundService sut = new DeleteSoundService(loadUniversePort, handleSoundEventPort, fileDeleteEventPublisher);

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
        verify(handleSoundEventPort, times(1)).handleSoundDeleteEvent(any());
        verify(fileDeleteEventPublisher, times(1)).publishDeleteFilesEvent((UUID) any());
    }

}