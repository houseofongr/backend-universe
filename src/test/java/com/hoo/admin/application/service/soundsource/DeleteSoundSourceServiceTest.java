package com.hoo.admin.application.service.soundsource;

import com.hoo.admin.application.port.out.soundsource.DeleteSoundSourcePort;
import com.hoo.admin.application.port.out.soundsource.FindSoundSourcePort;
import com.hoo.common.application.port.in.MessageDto;
import com.hoo.common.application.service.MockEntityFactoryService;
import com.hoo.file.application.port.in.DeleteFileUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DeleteSoundSourceServiceTest {

    DeleteSoundSourceService sut;

    FindSoundSourcePort findSoundSourcePort;
    DeleteSoundSourcePort deleteSoundSourcePort;
    DeleteFileUseCase deleteFileUseCase;

    @BeforeEach
    void init() {
        findSoundSourcePort = mock();
        deleteSoundSourcePort = mock();
        deleteFileUseCase = mock();
        sut = new DeleteSoundSourceService(findSoundSourcePort, deleteSoundSourcePort, deleteFileUseCase);
    }

    @Test
    @DisplayName("음원 삭제 서비스 테스트")
    void testDeleteService() throws Exception {
        // given
        Long soundSourceId = 1L;

        // when
        when(findSoundSourcePort.loadSoundSource(soundSourceId)).thenReturn(Optional.of(MockEntityFactoryService.getSoundSource()));
        MessageDto messageDto = sut.deleteSoundSource(soundSourceId);

        // then
        verify(deleteSoundSourcePort, times(1)).deleteSoundSource(any());
        verify(deleteFileUseCase, times(1)).deleteFile(any());
        assertThat(messageDto.message()).isEqualTo("1번 음원이 삭제되었습니다.");
    }
}