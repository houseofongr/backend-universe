package com.hoo.admin.application.port.in.item;

import com.hoo.admin.domain.item.Item;
import com.hoo.admin.domain.item.soundsource.SoundSource;
import com.hoo.common.adapter.in.web.DateTimeFormatters;

import java.util.List;

public record QueryItemResult(
        String itemName,
        List<SoundSourceInfo> soundSource
) {
    public static QueryItemResult of(Item item) {
        return new QueryItemResult(
                item.getItemDetail().getName(),
                item.getSoundSources().stream().map(SoundSourceInfo::of).toList()
        );
    }

    public record SoundSourceInfo(
            Long id,
            String name,
            String description,
            String createdDate,
            String updatedDate,
            Long audioFileId
    ) {

        public static SoundSourceInfo of(SoundSource soundSource) {
            return new SoundSourceInfo(
                    soundSource.getSoundSourceId().getId(),
                    soundSource.getSoundSourceDetail().getName(),
                    soundSource.getSoundSourceDetail().getDescription(),
                    DateTimeFormatters.DOT_DATE.getFormatter().format(soundSource.getBaseTime().getCreatedTime()),
                    DateTimeFormatters.DOT_DATE.getFormatter().format(soundSource.getBaseTime().getUpdatedTime()),
                    soundSource.getFile().getFileId().getId()
            );
        }
    }
}
