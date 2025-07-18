package com.hoo.admin.application.port.in.universe;

import com.hoo.admin.application.port.in.category.CategoryInfo;

import java.util.List;

public record UpdateUniverseResult() {

    public record Detail(
            String message,
            Long authorId,
            Long updatedTime,
            String title,
            String description,
            String author,
            String publicStatus,
            CategoryInfo category,
            List<String> hashtags
    ) {
    }

    public record Thumbnail(
            String message,
            Long deletedThumbnailId,
            Long newThumbnailId
    ) {
        public static Thumbnail of(Long universeId, Long deletedThumbnailId, Long newThumbnailId) {
            return new Thumbnail(
                    String.format("[#%d]번 유니버스의 썸네일이 수정되었습니다.", universeId),
                    deletedThumbnailId,
                    newThumbnailId);
        }
    }

    public record ThumbMusic(
            String message,
            Long deletedThumbMusicId,
            Long newThumbMusicId
    ) {
        public static ThumbMusic of(Long universeId, Long deletedThumbMusicId, Long newThumbMusicId) {
            return new ThumbMusic(
                    String.format("[#%d]번 유니버스의 썸뮤직이 수정되었습니다.", universeId),
                    deletedThumbMusicId,
                    newThumbMusicId);
        }
    }

    public record InnerImage(
            String message,
            Long deletedInnerImageId,
            Long newInnerImageId
    ) {
        public static InnerImage of(Long universeId, Long deletedInnerImageId, Long newInnerImageId) {
            return new InnerImage(
                    String.format("[#%d]번 유니버스의 내부이미지가 수정되었습니다.", universeId),
                    deletedInnerImageId,
                    newInnerImageId);
        }
    }
}
