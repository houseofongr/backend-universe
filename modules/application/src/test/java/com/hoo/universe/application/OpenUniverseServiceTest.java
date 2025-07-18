package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverseOnly;
import static org.mockito.Mockito.*;

class OpenUniverseServiceTest {

    LoadUniversePort loadUniversePort = mock();

    OpenUniverseService sut = new OpenUniverseService(loadUniversePort);

    @Test
    @DisplayName("유니버스 열기 서비스")
    void testOpenUniverse() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        Universe universe = defaultUniverseOnly().build();

        // when
        when(loadUniversePort.loadUniverseExceptSounds(universeID)).thenReturn(universe);
        sut.openUniverseWithComponents(universeID);

        // then
        verify(loadUniversePort, times(1)).loadUniverseExceptSounds(universeID);
    }
}