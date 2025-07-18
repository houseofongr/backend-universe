package com.hoo.admin.domain.universe;

import lombok.Getter;

@Getter
public class SpacePieceBasicInfo extends BaseBasicInfo {

    private final Long universeId;
    private final Long parentSpaceId;
    private Boolean hidden;

    public SpacePieceBasicInfo(Long universeId, Long parentSpaceId, String title, String description, Boolean hidden) {
        super(title, description);
        this.universeId = universeId;
        this.parentSpaceId = parentSpaceId != null && parentSpaceId == -1? null : parentSpaceId;
        this.hidden = hidden;
    }

    public void updateHiddenStatus(Boolean hidden) {
        this.hidden = hidden != null? hidden : this.hidden;
    }

}
