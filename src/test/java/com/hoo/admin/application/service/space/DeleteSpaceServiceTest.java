package com.hoo.admin.application.service.space;

import com.hoo.admin.application.port.in.piece.DeletePieceResult;
import com.hoo.admin.application.port.in.piece.DeletePieceUseCase;
import com.hoo.admin.application.port.in.space.DeleteSpaceResult;
import com.hoo.admin.application.port.out.space.DeleteSpacePort;
import com.hoo.admin.application.port.out.space.FindSpacePort;
import com.hoo.admin.application.port.out.universe.FindUniversePort;
import com.hoo.admin.domain.universe.MockTreeInfo;
import com.hoo.admin.domain.universe.TraversalComponents;
import com.hoo.file.application.port.in.DeleteFileUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DeleteSpaceServiceTest {

    FindSpacePort findSpacePort = mock();
    FindUniversePort findUniversePort = mock();
    DeleteSpacePort deleteSpacePort = mock();
    DeletePieceUseCase deletePieceUseCase = mock();
    DeleteFileUseCase deleteFileUseCase = mock();

    DeleteSpaceService sut = new DeleteSpaceService(findSpacePort, findUniversePort, deleteSpacePort, deletePieceUseCase, deleteFileUseCase);

    @Test
    @DisplayName("스페이스 삭제 서비스")
    void testDeleteSpaceService() {
        // given
        Long spaceId = 2L;
        TraversalComponents traversalComponent = MockTreeInfo.getTraversalComponent();

        // when
        when(findSpacePort.findUniverseId(spaceId)).thenReturn(1L);
        when(findUniversePort.findTreeComponents(1L)).thenReturn(traversalComponent);
        when(deletePieceUseCase.delete(anyLong())).thenReturn(new DeletePieceResult("test", 1L, List.of(), List.of(), List.of()));
        DeleteSpaceResult result = sut.delete(spaceId);

        // then
        verify(deleteSpacePort, times(1)).deleteAll(anyList());
        verify(deletePieceUseCase, times(4)).delete(anyLong());
        verify(deleteFileUseCase, times(1)).deleteFiles(anyList());
        assertThat(result.message()).matches("\\[#\\d+]번 스페이스가 삭제되었습니다.");
        assertThat(result.deletedAudioFileIds()).isEmpty();
        assertThat(result.deletedImageFileIds()).hasSize(4);
        assertThat(result.deletedSpaceIds()).hasSize(3)
                .anyMatch(id -> id.equals(2L))
                .anyMatch(id -> id.equals(4L))
                .anyMatch(id -> id.equals(5L));

        assertThat(result.deletedPieceIds()).hasSize(4)
                .anyMatch(id -> id.equals(4L))
                .anyMatch(id -> id.equals(5L))
                .anyMatch(id -> id.equals(6L))
                .anyMatch(id -> id.equals(7L));
    }

}