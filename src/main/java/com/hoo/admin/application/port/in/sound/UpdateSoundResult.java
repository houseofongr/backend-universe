package com.hoo.admin.application.port.in.sound;

public record UpdateSoundResult(

) {
    public record Detail(
            String message,
            String title,
            String description,
            Boolean hidden
    ) {

    }

    public record Audio(
            String message,
            Long deletedAudioId,
            Long newAudioId
    ) {

    }
}
