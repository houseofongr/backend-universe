package com.hoo.admin.application.service.sound;

import com.hoo.admin.application.port.in.sound.DeleteSoundResult;
import com.hoo.admin.application.port.out.sound.DeleteSoundPort;
import com.hoo.admin.application.port.out.sound.FindSoundPort;
import com.hoo.admin.domain.universe.piece.sound.Sound;
import com.hoo.file.application.port.in.DeleteFileUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DeleteSoundServiceTest {

    FindSoundPort findSoundPort = mock();
    DeleteFileUseCase deleteFileUseCase = mock();
    DeleteSoundPort deleteSoundPort = mock();
    DeleteSoundService sut = new DeleteSoundService(findSoundPort, deleteFileUseCase, deleteSoundPort);

    @Test
    @DisplayName("사운드 삭제 서비스")
    void testDeleteSound() {
        // given
        Long id = 1L;
        Sound sound = Sound.create(1L, 234L, 1L, "소리", "사운드는 소리입니다.", false);

        // when
        when(findSoundPort.find(1L)).thenReturn(sound);
        DeleteSoundResult result = sut.delete(id);

        // then
        verify(deleteSoundPort, times(1)).delete(sound.getId());
        assertThat(result.message()).matches("\\[#\\d+]번 사운드가 삭제되었습니다.");
        assertThat(result.deletedAudioId()).isEqualTo(234L);
    }

}