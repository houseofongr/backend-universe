package com.hoo.admin.application.service.soundsource;

import com.hoo.admin.application.port.in.soundsource.QuerySoundSourceListCommand;
import com.hoo.admin.application.port.in.soundsource.QuerySoundSourceResult;
import com.hoo.admin.application.port.out.soundsource.FindSoundSourcePort;
import com.hoo.admin.application.port.out.soundsource.QuerySoundSourcePort;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class QuerySoundSourceServiceTest {

    QuerySoundSourceService sut;

    FindSoundSourcePort findSoundSourcePort;
    QuerySoundSourcePort querySoundSourcePort;

    @BeforeEach
    void init() {
        findSoundSourcePort = mock();
        querySoundSourcePort = mock();
        sut = new QuerySoundSourceService(findSoundSourcePort, querySoundSourcePort);
    }

    @Test
    @DisplayName("음원 조회 서비스 테스트")
    void testQuerySoundSource() throws Exception {
        // given
        Long id = 1L;

        // when
        when(findSoundSourcePort.loadSoundSource(id)).thenReturn(Optional.of(MockEntityFactoryService.loadSoundSource()));
        QuerySoundSourceResult querySoundSourceResult = sut.querySoundSource(id);

        // then
        assertThat(querySoundSourceResult).isNotNull();
        assertThat(querySoundSourceResult.createdDate()).matches("\\d{4}\\.\\d{2}\\.\\d{2}\\.");
    }

    @Test
    @DisplayName("음원 리스트 조회 서비스 테스트")
    void testQuerySoundSourceListService() {
        // given
        QuerySoundSourceListCommand command = new QuerySoundSourceListCommand(PageRequest.of(1, 3));

        // when
        sut.querySoundSourceList(command);

        // then
        verify(querySoundSourcePort, times(1)).querySoundSourceList(command);
    }
}