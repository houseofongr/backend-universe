package com.hoo.admin.domain.universe.piece.sound;

import com.hoo.admin.domain.universe.DateInfo;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class Sound {
    private final Long id;
    private final AudioFileInfo fileInfo;
    private final SoundBasicInfo basicInfo;
    private final DateInfo dateInfo;

    private Sound(Long id, AudioFileInfo fileInfo, SoundBasicInfo basicInfo, DateInfo dateInfo) {
        this.id = id;
        this.fileInfo = fileInfo;
        this.basicInfo = basicInfo;
        this.dateInfo = dateInfo;
    }

    public static Sound create(Long id, Long audioId, Long pieceId, String title, String description, Boolean hidden) {
        return new Sound(id,
                new AudioFileInfo(audioId),
                new SoundBasicInfo(pieceId, title, description, hidden),
                null
        );
    }

    public static Sound loadWithoutRelation(Long id, Long audioId, String title, String description, Boolean hidden, ZonedDateTime createdTime, ZonedDateTime updatedTime) {
        return new Sound(id,
                new AudioFileInfo(audioId),
                new SoundBasicInfo(null, title, description, hidden),
                new DateInfo(createdTime, updatedTime)
        );
    }

    public static Sound loadFile(Long id, Long audioId) {
        return new Sound(id,
                new AudioFileInfo(audioId),
                null,
                null
        );
    }
}
