package com.hoo.admin.application.service.piece;

import com.hoo.admin.application.port.in.piece.DeletePieceResult;
import com.hoo.admin.application.port.out.piece.DeletePiecePort;
import com.hoo.admin.application.port.out.piece.FindPiecePort;
import com.hoo.admin.application.port.out.sound.DeleteSoundPort;
import com.hoo.admin.domain.universe.piece.Piece;
import com.hoo.admin.domain.universe.piece.sound.Sound;
import com.hoo.file.application.port.in.DeleteFileUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DeletePieceServiceTest {

    FindPiecePort findPiecePort = mock();
    DeletePiecePort deletePiecePort = mock();
    DeleteSoundPort deleteSoundPort = mock();
    DeleteFileUseCase deleteFileUseCase = mock();

    DeletePieceService sut = new DeletePieceService(findPiecePort, deletePiecePort, deleteSoundPort, deleteFileUseCase);

    @Test
    @DisplayName("피스 삭제 서비스")
    void testDeletePieceService() {
        // given
        Long id = 1L;
        List<Sound> sounds = List.of(Sound.loadFile(1L, 2L), Sound.loadFile(2L, 3L));
        Piece piece = Piece.loadWithSound(1L, 1L, sounds);

        // when
        when(findPiecePort.findWithSounds(id)).thenReturn(piece);
        DeletePieceResult result = sut.delete(id);

        // then
        assertThat(result.message()).matches("\\[#\\d+]번 피스가 삭제되었습니다.");
        assertThat(result.deletedSoundIds()).hasSize(2)
                .anyMatch(soundId -> soundId.equals(1L))
                .anyMatch(soundId -> soundId.equals(2L));
        assertThat(result.deletedAudioFileIds()).hasSize(2)
                .anyMatch(audioFileId -> audioFileId.equals(2L))
                .anyMatch(audioFileId -> audioFileId.equals(3L));
        assertThat(result.deletedImageFileIds()).allMatch(imageFileId -> imageFileId.equals(1L));
    }

}