package com.hoo.admin.domain.universe;

import lombok.Getter;

@Getter
public class UniverseBasicInfo extends BaseBasicInfo {
    private PublicStatus publicStatus;

    public UniverseBasicInfo(String title, String description, PublicStatus publicStatus) {
        super(title, description);
        this.publicStatus = publicStatus;
    }

    public void updateUniverseInfo(String title, String description, PublicStatus publicStatus) {
        super.update(title, description);
        this.publicStatus = publicStatus != null ? publicStatus : this.publicStatus;
    }

}
