package com.hoo.admin.domain.universe.piece.sound;

import com.hoo.admin.domain.universe.BaseBasicInfo;
import lombok.Getter;

@Getter
public class SoundBasicInfo extends BaseBasicInfo {

    private final Long pieceId;
    private Boolean hidden;

    public SoundBasicInfo(Long pieceId, String title, String description, Boolean hidden) {
        super(title, description);
        this.pieceId = pieceId;
        this.hidden = hidden;
    }

    public void updateHideStatus(Boolean hidden) {
        this.hidden = hidden;
    }
}
