package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.common.web.dto.Pagination;
import com.hoo.universe.api.in.dto.SearchUniverseCommand;
import com.hoo.universe.api.in.dto.UniverseListInfo;
import com.hoo.universe.api.out.FileUrlResolveInCase;
import com.hoo.universe.api.out.QueryUniversePort;
import com.hoo.universe.api.out.dto.UniverseListQueryInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.hoo.universe.test.dto.PageableTestData.defaultPageable;
import static org.mockito.Mockito.*;

class SearchUniverseServiceTest {

    QueryUniversePort queryUniversePort = mock();
    FileUrlResolveInCase fileUrlResolveInCase = mock();
    FileOwnerMapExtractor fileOwnerMapExtractor = mock();
    ApplicationMapper applicationMapper = mock();

    SearchUniverseService sut = new SearchUniverseService(queryUniversePort, fileUrlResolveInCase,fileOwnerMapExtractor, applicationMapper);

    @Test
    @DisplayName("유니버스 검색 서비스")
    void searchUniverseService() {
        // given
        SearchUniverseCommand command = mock(SearchUniverseCommand.class);
        PageQueryResult<UniverseListQueryInfo> queryResult = mock(PageQueryResult.class);
        Map<UUID, UUID> fileOwnerMap = mock(Map.class);
        Map<UUID, URI> fileUrlMap = mock(Map.class);

        when(queryUniversePort.searchUniverses(command)).thenReturn(queryResult);
        when(fileOwnerMapExtractor.extractFileOwnerMap(queryResult)).thenReturn(fileOwnerMap);
        when(fileUrlResolveInCase.resolveBatch(fileOwnerMap)).thenReturn(fileUrlMap);
        when(applicationMapper.mapToUniverseListInfoPageQueryResult(queryResult, fileUrlMap))
                .thenReturn(mock(PageQueryResult.class));

        // when
        sut.searchUniverse(command);

        // then
        verify(queryUniversePort, times(1)).searchUniverses(command);
        verify(fileOwnerMapExtractor, times(1)).extractFileOwnerMap(queryResult);
        verify(fileUrlResolveInCase, times(1)).resolveBatch(fileOwnerMap);
        verify(applicationMapper, times(1)).mapToUniverseListInfoPageQueryResult(queryResult, fileUrlMap);
    }
}