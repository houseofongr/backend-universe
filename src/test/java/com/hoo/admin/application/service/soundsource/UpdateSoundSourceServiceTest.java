package com.hoo.admin.application.service.soundsource;

import com.hoo.admin.application.port.in.soundsource.UpdateSoundSourceCommand;
import com.hoo.admin.application.port.out.soundsource.FindSoundSourcePort;
import com.hoo.admin.application.port.out.soundsource.UpdateSoundSourcePort;
import com.hoo.common.application.port.in.MessageDto;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UpdateSoundSourceServiceTest {

    UpdateSoundSourceService sut;

    FindSoundSourcePort findSoundSourcePort;
    UpdateSoundSourcePort updateSoundSourcePort;

    @BeforeEach
    void init() {
        findSoundSourcePort = mock();
        updateSoundSourcePort = mock();
        sut = new UpdateSoundSourceService(findSoundSourcePort, updateSoundSourcePort);
    }

    @Test
    @DisplayName("음원 수정 서비스 테스트")
    void testUpdateSoundSourceService() throws Exception {
        // given
        Long soundSourceId = 1L;
        UpdateSoundSourceCommand command = new UpdateSoundSourceCommand("골골골송", "2026년 설이가 보내는 골골골송", false);

        // when
        when(findSoundSourcePort.loadSoundSource(soundSourceId)).thenReturn(Optional.of(MockEntityFactoryService.getSoundSource()));
        MessageDto messageDto = sut.updateSoundSource(soundSourceId, command);

        // then
        verify(updateSoundSourcePort, times(1)).updateSoundSource(any());
        assertThat(messageDto.message()).isEqualTo("1번 음원의 정보가 수정되었습니다.");
    }
}