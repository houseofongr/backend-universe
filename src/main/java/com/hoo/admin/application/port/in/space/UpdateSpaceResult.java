package com.hoo.admin.application.port.in.space;

import com.hoo.admin.domain.universe.space.Space;

public record UpdateSpaceResult() {
    public record Detail(
            String message,
            String title,
            String description,
            Boolean hidden
    ) {
        public static Detail of(Space space) {
            return new Detail(
                    String.format("[#%d]번 스페이스의 상세정보가 수정되었습니다.", space.getId()),
                    space.getBasicInfo().getTitle(),
                    space.getBasicInfo().getDescription(),
                    space.getBasicInfo().getHidden()
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
        public static Position of(Space space) {
            return new Position(
                    String.format("[#%d]번 스페이스의 좌표가 수정되었습니다.", space.getId()),
                    space.getPosInfo().getSx(),
                    space.getPosInfo().getSy(),
                    space.getPosInfo().getEx(),
                    space.getPosInfo().getEy()
            );
        }
    }

    public record InnerImage(
            String message,
            Long deletedInnerImageId,
            Long newInnerImageId
    ) {

        public static InnerImage of(Long spaceId, Long deletedInnerImageId, Long newInnerImageId) {
            return new InnerImage(
                    String.format("[#%d]번 스페이스의 내부이미지가 수정되었습니다.", spaceId),
                    deletedInnerImageId,
                    newInnerImageId
            );
        }

    }
}
