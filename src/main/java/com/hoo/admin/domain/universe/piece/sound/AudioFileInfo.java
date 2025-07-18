package com.hoo.admin.domain.universe.piece.sound;

import lombok.Getter;

@Getter
public class AudioFileInfo {

    private Long audioId;

    public AudioFileInfo(Long audioId) {
        this.audioId = audioId;
    }

    public void updateAudio(Long audioId) {
        this.audioId = audioId;
    }
}
