package com.hoo.admin.application.service.universe;

import com.hoo.admin.application.port.in.space.DeleteSpaceResult;
import com.hoo.admin.application.port.in.space.DeleteSpaceUseCase;
import com.hoo.admin.application.port.in.universe.DeleteUniverseResult;
import com.hoo.admin.application.port.out.universe.DeleteUniversePort;
import com.hoo.admin.application.port.out.universe.FindUniversePort;
import com.hoo.admin.domain.universe.MockTreeInfo;
import com.hoo.admin.domain.universe.TraversalComponents;
import com.hoo.file.application.port.in.DeleteFileUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DeleteUniverseServiceTest {

    FindUniversePort findUniversePort = mock();
    DeleteUniversePort deleteUniversePort = mock();
    DeleteSpaceUseCase deleteSpaceUseCase = mock();
    DeleteFileUseCase deleteFileUseCase = mock();

    DeleteUniverseService sut = new DeleteUniverseService(findUniversePort, deleteUniversePort, deleteSpaceUseCase, deleteFileUseCase);

    @Test
    @DisplayName("썸네일 및 썸뮤직 파일 삭제요청, DB 삭제요청")
    void testDeleteUniverse() {
        // given
        Long id = 1L;
        TraversalComponents traversalComponent = MockTreeInfo.getTraversalComponent();

        // when
        when(findUniversePort.findTreeComponents(1L)).thenReturn(traversalComponent);
        when(deleteSpaceUseCase.deleteSubtree(anyLong(), any())).thenReturn(new DeleteSpaceResult("test", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));


        DeleteUniverseResult result = sut.delete(id);

        // then
        verify(deleteFileUseCase, times(1)).deleteFiles(any());
        verify(deleteUniversePort, times(1)).delete(any());
        assertThat(result.message()).contains("번 유니버스가 삭제되었습니다.");
    }

}