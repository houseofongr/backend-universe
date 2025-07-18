package com.hoo.admin.application.port.in.space;

import java.util.Comparator;
import java.util.List;

public record DeleteSpaceResult(
        String message,
        List<Long> deletedSpaceIds,
        List<Long> deletedPieceIds,
        List<Long> deletedSoundIds,
        List<Long> deletedImageFileIds,
        List<Long> deletedAudioFileIds
) {
    public void sort() {
        this.deletedSpaceIds.sort(Comparator.naturalOrder());
        this.deletedPieceIds.sort(Comparator.naturalOrder());
        this.deletedSoundIds.sort(Comparator.naturalOrder());
        this.deletedImageFileIds.sort(Comparator.naturalOrder());
        this.deletedAudioFileIds.sort(Comparator.naturalOrder());
    }
}
