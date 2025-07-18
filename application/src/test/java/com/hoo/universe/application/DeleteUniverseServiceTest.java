package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.internal.message.FileDeleteEventPublisher;
import com.hoo.universe.api.out.persistence.HandleUniverseEventPort;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverse;
import static org.mockito.Mockito.*;

class DeleteUniverseServiceTest {

    LoadUniversePort loadUniversePort = mock();
    HandleUniverseEventPort handleUniverseEventPort = mock();
    FileDeleteEventPublisher fileDeleteEventPublisher = mock();

    DeleteUniverseService sut = new DeleteUniverseService(loadUniversePort, handleUniverseEventPort, fileDeleteEventPublisher);

    @Test
    @DisplayName("유니버스 삭제 서비스")
    void deleteUniverseService() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        Universe universe = defaultUniverse();

        // when
        when(loadUniversePort.loadUniverseWithAllEntity(universeID)).thenReturn(universe);
        sut.deleteUniverse(universeID);

        // then
        verify(handleUniverseEventPort, times(1)).handleUniverseDeleteEvent(any());
        verify(fileDeleteEventPublisher, times(1)).publishDeleteFilesEvent((List) any());
    }

}