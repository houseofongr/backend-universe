package com.hoo.admin.domain.universe;

import lombok.Getter;

@Getter
public class UniverseFileInfo extends ImageFileInfo {

    private Long thumbMusicId;
    private Long thumbnailId;

    public UniverseFileInfo(Long thumbMusicId, Long thumbnailId, Long innerImageId) {
        super(innerImageId);
        this.thumbMusicId = thumbMusicId;
        this.thumbnailId = thumbnailId;
    }

    public void updateThumbnail(Long thumbnailId) {
        this.thumbnailId = thumbnailId;
    }

    public void updateThumbMusic(Long thumbMusicId) {
        this.thumbMusicId = thumbMusicId;
    }
}
