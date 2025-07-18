package com.hoo.admin.application.service.piece;

import com.hoo.admin.application.port.in.piece.UpdatePieceCommand;
import com.hoo.admin.application.port.in.piece.UpdatePieceResult;
import com.hoo.admin.application.port.out.piece.FindPiecePort;
import com.hoo.admin.application.port.out.piece.UpdatePiecePort;
import com.hoo.admin.domain.universe.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UpdatePieceServiceTest {

    FindPiecePort findPiecePort = mock();
    UpdatePiecePort updatePiecePort = mock();

    UpdatePieceService sut = new UpdatePieceService(findPiecePort, updatePiecePort);

    @Test
    @DisplayName("피스 수정 서비스")
    void testUpdateDetailPiece() {
        // given
        Long pieceId = 1L;
        UpdatePieceCommand.Detail command = new UpdatePieceCommand.Detail("평화", "피스는 평화입니다.", true);
        Piece newPiece = Piece.create(1L, -1L, 123L, 321L, "피스", "피스는 조각입니다.", 0.1f, 0.2f, 0.3f, 0.4f, false);

        // when
        when(findPiecePort.find(pieceId)).thenReturn(newPiece);
        UpdatePieceResult.Detail result = sut.updateDetail(pieceId, command);

        // then
        verify(updatePiecePort, times(1)).update(any(), any());
        assertThat(result.message()).matches("\\[#\\d+]번 피스의 상세정보가 수정되었습니다.");
        assertThat(result.title()).isEqualTo("평화");
        assertThat(result.description()).isEqualTo("피스는 평화입니다.");
        assertThat(result.hidden()).isTrue();
    }

    @Test
    @DisplayName("피스 좌표 수정 서비스")
    void testUpdatePositionPiece() {
        // given
        Long pieceId = 1L;
        UpdatePieceCommand.Position command = new UpdatePieceCommand.Position(0.5f, 0.6f, 0.7f, 0.8f);
        Piece newPiece = Piece.create(1L, -1L, 123L, 321L, "피스", "피스는 조각입니다.", 0.1f, 0.2f, 0.3f, 0.4f, false);

        // when
        when(findPiecePort.find(pieceId)).thenReturn(newPiece);
        UpdatePieceResult.Position result = sut.updatePosition(pieceId, command);

        // then
        verify(updatePiecePort, times(1)).update(any(), any());
        assertThat(result.message()).matches("\\[#\\d+]번 피스의 좌표가 수정되었습니다.");
        assertThat(result.startX()).isEqualTo(0.5f);
        assertThat(result.startY()).isEqualTo(0.6f);
        assertThat(result.endX()).isEqualTo(0.7f);
        assertThat(result.endY()).isEqualTo(0.8f);
    }

}