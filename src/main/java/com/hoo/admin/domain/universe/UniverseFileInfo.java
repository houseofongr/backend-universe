package com.hoo.admin.domain.universe;

import lombok.Getter;

@Getter
public class UniverseFileInfo extends ImageFileInfo {

    private Long thumbmusicId;
    private Long thumbnailId;

    public UniverseFileInfo(Long thumbmusicId, Long thumbnailId, Long innerImageId) {
        super(innerImageId);
        this.thumbmusicId = thumbmusicId;
        this.thumbnailId = thumbnailId;
    }

    public void updateThumbnail(Long thumbnailId) {
        this.thumbnailId = thumbnailId;
    }

    public void updateThumbMusic(Long thumbmusicId) {
        this.thumbmusicId = thumbmusicId;
    }
}
