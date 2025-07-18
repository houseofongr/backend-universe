package com.hoo.admin.domain.universe;

import lombok.Getter;

@Getter
public class ImageFileInfo {

    private Long imageId;

    public ImageFileInfo(Long imageId) {
        this.imageId = imageId != null && imageId == -1? null : imageId;
    }

    public void updateImage(Long imageId) {
        this.imageId = imageId;
    }
}
