package com.hoo.aar.application.service.home;

import com.hoo.aar.application.port.in.home.QuerySoundSourcesPathCommand;
import com.hoo.aar.application.port.out.persistence.home.CheckOwnerPort;
import com.hoo.aar.application.port.out.persistence.home.QueryHomePort;
import com.hoo.aar.application.service.AarErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class QueryHomeServiceTest {

    QueryHomeService sut;

    CheckOwnerPort checkOwnerPort;
    QueryHomePort queryHomePort;

    @BeforeEach
    void init() {
        checkOwnerPort = mock();
        queryHomePort = mock();
        sut = new QueryHomeService(checkOwnerPort, queryHomePort);
    }

    @Test
    @DisplayName("유저 홈 조회 서비스 테스트")
    void testQueryUserHomes() {
        // given
        Long userId = 1L;

        // when
        sut.queryUserHomes(userId);

        // then
        verify(queryHomePort, times(1)).queryUserHomes(userId);
    }

    @Test
    @DisplayName("홈 룸 조회 서비스 테스트")
    void testQueryHomeRooms() {
        // given
        Long userId = 10L;
        Long homeId = 1L;

        // when
        when(checkOwnerPort.checkHome(userId, homeId)).thenReturn(true);
        assertThatThrownBy(() -> sut.queryHomeRooms(123L, homeId)).hasMessage(AarErrorCode.NOT_OWNED_HOME.getMessage());
        sut.queryHomeRooms(userId, homeId);

        // then
        verify(queryHomePort, times(1)).queryHomeRooms(homeId);
    }

    @Test
    @DisplayName("룸 아이템 조회 서비스 테스트")
    void testQueryRoomItems() {
        // given
        Long userId = 10L;
        Long homeId = 1L;
        Long roomId = 2L;

        // when
        when(checkOwnerPort.checkHome(userId, homeId)).thenReturn(true);
        when(checkOwnerPort.checkRoom(homeId, roomId)).thenReturn(true);
        assertThatThrownBy(() -> sut.queryRoomItems(1234L, homeId, roomId)).hasMessage(AarErrorCode.NOT_OWNED_HOME.getMessage());
        assertThatThrownBy(() -> sut.queryRoomItems(userId, 1234L, roomId)).hasMessage(AarErrorCode.NOT_OWNED_HOME.getMessage());
        assertThatThrownBy(() -> sut.queryRoomItems(userId, homeId, 1234L)).hasMessage(AarErrorCode.NOT_OWNED_ROOM.getMessage());
        sut.queryRoomItems(userId, homeId, roomId);

        // then
        verify(queryHomePort, times(1)).queryRoomItems(homeId, roomId);
    }

    @Test
    @DisplayName("아이템 음원 조회 서비스 테스트")
    void testQueryItemSoundSources() {
        // given
        Long userId = 10L;
        Long itemId = 1L;

        // when
        when(checkOwnerPort.checkItem(userId, itemId)).thenReturn(true);
        assertThatThrownBy(() -> sut.queryItemSoundSources(1234L, itemId)).hasMessage(AarErrorCode.NOT_OWNED_ITEM.getMessage());
        assertThatThrownBy(() -> sut.queryItemSoundSources(userId, 1234L)).hasMessage(AarErrorCode.NOT_OWNED_ITEM.getMessage());
        sut.queryItemSoundSources(userId, itemId);

        // then
        verify(queryHomePort, times(1)).queryItemSoundSources(itemId);
    }

    @Test
    @DisplayName("음원 조회 서비스 테스트")
    void testQuerySoundSource() {
        // given
        Long userId = 10L;
        Long soundSourceId = 1L;

        // when
        when(checkOwnerPort.checkSoundSource(userId, soundSourceId)).thenReturn(true);
        assertThatThrownBy(() -> sut.querySoundSource(1234L, soundSourceId)).hasMessage(AarErrorCode.NOT_OWNED_SOUND_SOURCE.getMessage());
        assertThatThrownBy(() -> sut.querySoundSource(userId, 1234L)).hasMessage(AarErrorCode.NOT_OWNED_SOUND_SOURCE.getMessage());
        sut.querySoundSource(userId, soundSourceId);

        // then
        verify(queryHomePort, times(1)).querySoundSource(soundSourceId);
    }

    @Test
    @DisplayName("전체음원 경로 조회 서비스 테스트")
    void testQuerySoundSourcesPath() {
        // given
        QuerySoundSourcesPathCommand command = new QuerySoundSourcesPathCommand(10L, PageRequest.of(1, 3));

        // when
        sut.querySoundSourcesPath(command);

        // then
        verify(queryHomePort, times(1)).querySoundSourcesPath(command);
    }
}