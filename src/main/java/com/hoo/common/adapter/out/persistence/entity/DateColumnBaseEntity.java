package com.hoo.common.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@MappedSuperclass
public class DateColumnBaseEntity {

    @Column
    @CreatedDate
    private ZonedDateTime createdTime;

    @Column
    @LastModifiedDate
    private ZonedDateTime updatedTime;

    @PrePersist
    public void prePersist() {
        this.createdTime = ZonedDateTime.now(ZoneId.systemDefault());
        this.updatedTime = ZonedDateTime.now(ZoneId.systemDefault());
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedTime = ZonedDateTime.now(ZoneId.systemDefault());
    }
}
