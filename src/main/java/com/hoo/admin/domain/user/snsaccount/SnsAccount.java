package com.hoo.admin.domain.user.snsaccount;

import com.hoo.admin.domain.user.UserId;
import com.hoo.common.domain.BaseTime;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class SnsAccount {
    private final SnsAccountId snsAccountId;
    private final SnsAccountInfo snsAccountInfo;
    private final BaseTime baseTime;
    private UserId userId;

    private SnsAccount(SnsAccountId snsAccountId, SnsAccountInfo snsAccountInfo, BaseTime baseTime, UserId userId) {
        this.snsAccountId = snsAccountId;
        this.snsAccountInfo = snsAccountInfo;
        this.baseTime = baseTime;
        this.userId = userId;
    }

    public static SnsAccount create(Long persistenceId, SnsDomain snsDomain, String snsId, String realName, String nickname, String email) {
        return new SnsAccount(
                new SnsAccountId(persistenceId, snsDomain, snsId),
                new SnsAccountInfo(realName, nickname, email),
                new BaseTime(null, null),
                new UserId(null)
        );
    }

    public static SnsAccount load(Long persistenceId, SnsDomain snsDomain, String snsId, String realName, String nickname, String email, ZonedDateTime createdTime, ZonedDateTime updatedTime, Long userId) {
        return new SnsAccount(
                new SnsAccountId(persistenceId, snsDomain, snsId),
                new SnsAccountInfo(realName, nickname, email),
                new BaseTime(createdTime, updatedTime),
                new UserId(userId)
        );
    }

    public void link(Long userId) {
        this.userId = new UserId(userId);
    }

    public boolean isRegistered() {
        return this.userId.getId() != null;
    }
}
