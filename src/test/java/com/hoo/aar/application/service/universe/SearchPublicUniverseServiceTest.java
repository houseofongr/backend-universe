package com.hoo.aar.application.service.universe;

import com.hoo.aar.application.port.in.universe.SearchPublicUniverseCommand;
import com.hoo.aar.application.port.in.universe.SearchPublicUniverseResult;
import com.hoo.aar.application.port.out.persistence.universe.SearchPublicUniversePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SearchPublicUniverseServiceTest {

    SearchPublicUniversePort searchPublicUniversePort = mock();

    SearchPublicUniverseService sut = new SearchPublicUniverseService(searchPublicUniversePort);

    @Test
    @DisplayName("공개 유니버스 조회 서비스")
    void testPublicUniverseService() {
        // given
        SearchPublicUniverseCommand command = new SearchPublicUniverseCommand(Pageable.ofSize(10), null, null, "life", "life", false);

        // when
        SearchPublicUniverseResult search = sut.search(command);

        // then
        verify(searchPublicUniversePort, times(1)).searchPublicUniverse(command);
    }
}