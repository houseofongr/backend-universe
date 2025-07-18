package com.hoo.admin.application.service.piece;

import com.hoo.admin.application.port.in.piece.CreatePieceCommand;
import com.hoo.admin.application.port.in.piece.CreatePieceResult;
import com.hoo.admin.application.port.out.piece.CreatePiecePort;
import com.hoo.admin.application.port.out.piece.SavePiecePort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.domain.universe.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class CreatePieceServiceTest {

    CreatePiecePort createPiecePort = mock();
    SavePiecePort savePiecePort = mock();
    CreatePieceService sut = new CreatePieceService(createPiecePort, savePiecePort);

    @Test
    @DisplayName("제목(100자/NB), 내용(5000자), 숨김 여부(hidden), 위치(startX, startY, scalex, scaley : 0 ~ 1) 조건 확인")
    void testBasicInfo() {
        String emptyTitle = "";
        String blankTitle = " ";
        String length100 = "a".repeat(101);
        String length5000 = "a".repeat(5001);

        assertThatThrownBy(() -> new CreatePieceCommand(1L, 1L, emptyTitle, null, 0.5F, 0.5F, 0.5F, 0.5F, false)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreatePieceCommand(1L, 1L, blankTitle, null, 0.5F, 0.5F, 0.5F, 0.5F, false)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreatePieceCommand(1L, 1L, length100, null, 0.5F, 0.5F, 0.5F, 0.5F, false)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreatePieceCommand(1L, 1L, "공간", length5000, 0.5F, 0.5F, 0.5F, 0.5F, false)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreatePieceCommand(1L, 1L, "공간", "공간공간", 0.5F, 0.5F, 0.5F, 0.5F, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());

        // null
        assertThatThrownBy(() -> new CreatePieceCommand(1L, 1L, "공간", null, null, 0.5F, 0.5F, 0.5F, false)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreatePieceCommand(1L, 1L, "공간", null, 0.5F, null, 0.5F, 0.5F, false)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreatePieceCommand(1L, 1L, "공간", null, 0.5F, 0.5F, null, 0.5F, false)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreatePieceCommand(1L, 1L, "공간", null, 0.5F, 0.5F, 0.5F, null, false)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());

        // 0 미만
        assertThatThrownBy(() -> new CreatePieceCommand(1L, 1L, "공간", null, -0.5F, 0.5F, 0.5F, 0.5F, false)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreatePieceCommand(1L, 1L, "공간", null, 0.5F, -0.5F, 0.5F, 0.5F, false)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreatePieceCommand(1L, 1L, "공간", null, 0.5F, 0.5F, -0.5F, 0.5F, false)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreatePieceCommand(1L, 1L, "공간", null, 0.5F, 0.5F, 0.5F, -0.5F, false)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());

        // 1 이상
        assertThatThrownBy(() -> new CreatePieceCommand(1L, 1L, "공간", null, 1.5F, 0.5F, 0.5F, 0.5F, false)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreatePieceCommand(1L, 1L, "공간", null, 0.5F, 1.5F, 0.5F, 0.5F, false)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreatePieceCommand(1L, 1L, "공간", null, 0.5F, 0.5F, 1.5F, 0.5F, false)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreatePieceCommand(1L, 1L, "공간", null, 0.5F, 0.5F, 0.5F, 1.5F, false)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("피스 생성 서비스")
    void TestCreatePieceService() {
        // given
        CreatePieceCommand command = new CreatePieceCommand(1L, 1L, "피스", "피스는 조각입니다.", 0.1f, 0.2f, 0.3f, 0.4f, false);
        Piece newPiece = Piece.create(1L, -1L, command.universeId(), command.parentSpaceId(), command.title(), command.description(), command.startX(), command.startY(), command.endX(), command.endY(), command.hidden());

        // when
        when(createPiecePort.createPieceWithoutImageFile(command)).thenReturn(newPiece);
        when(savePiecePort.save(any())).thenReturn(1L);
        CreatePieceResult result = sut.create(command);

        // then
        assertThat(result.message()).matches("\\[#\\d+]번 피스가 생성되었습니다.");
        assertThat(result.pieceId()).isEqualTo(1L);
        assertThat(result.title()).isEqualTo("피스");
        assertThat(result.description()).isEqualTo("피스는 조각입니다.");
        assertThat(result.startX()).isEqualTo(0.1f);
        assertThat(result.startY()).isEqualTo(0.2f);
        assertThat(result.endX()).isEqualTo(0.3f);
        assertThat(result.endY()).isEqualTo(0.4f);
    }
}