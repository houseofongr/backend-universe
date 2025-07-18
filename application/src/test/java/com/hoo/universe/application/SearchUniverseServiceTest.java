package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.dto.command.SearchUniverseCommand;
import com.hoo.universe.api.out.persistence.QueryUniversePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.hoo.universe.test.dto.PageableTestData.defaultPageable;
import static org.mockito.Mockito.*;

class SearchUniverseServiceTest {

    QueryUniversePort queryUniversePort = mock();

    SearchUniverseService sut = new SearchUniverseService(queryUniversePort);

    @Test
    @DisplayName("유니버스 검색 서비스")
    void searchUniverseService() {
        // given
        SearchUniverseCommand command = new SearchUniverseCommand(defaultPageable(), UuidCreator.getTimeOrderedEpoch());

        // when
        sut.searchUniverse(command);

        // then
        verify(queryUniversePort, times(1)).searchUniverses(any());
    }
}