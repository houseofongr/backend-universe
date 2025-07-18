package com.hoo.admin.application.port.in.piece;

import com.hoo.admin.domain.universe.piece.Piece;

public record UpdatePieceResult() {

    public record Detail(
            String message,
            String title,
            String description,
            Boolean hidden
    ) {

        public static UpdatePieceResult.Detail of(Piece piece) {
            return new UpdatePieceResult.Detail(
                    String.format("[#%d]번 피스의 상세정보가 수정되었습니다.", piece.getId()),
                    piece.getBasicInfo().getTitle(),
                    piece.getBasicInfo().getDescription(),
                    piece.getBasicInfo().getHidden()
            );
        }
    }

    public record Position(
            String message,
            Float startX,
            Float startY,
            Float endX,
            Float endY
    ) {
        public static UpdatePieceResult.Position of(Piece piece) {
            return new UpdatePieceResult.Position(
                    String.format("[#%d]번 피스의 좌표가 수정되었습니다.", piece.getId()),
                    piece.getPosInfo().getSx(),
                    piece.getPosInfo().getSy(),
                    piece.getPosInfo().getEx(),
                    piece.getPosInfo().getEy()
            );
        }
    }
}
