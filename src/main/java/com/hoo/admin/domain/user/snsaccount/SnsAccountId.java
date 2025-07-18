package com.hoo.admin.domain.user.snsaccount;

import lombok.Getter;

@Getter
public class SnsAccountId {

    private final Long persistenceId;
    private final String snsId;
    private final SnsDomain snsDomain;

    public SnsAccountId(Long persistenceId, SnsDomain snsDomain, String snsId) {
        this.persistenceId = persistenceId;
        this.snsDomain = snsDomain;
        this.snsId = snsId;
    }

}
