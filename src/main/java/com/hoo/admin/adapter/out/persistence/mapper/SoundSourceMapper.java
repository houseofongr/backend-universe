package com.hoo.admin.adapter.out.persistence.mapper;

import com.hoo.admin.application.port.in.soundsource.QuerySoundSourceListResult;
import com.hoo.admin.domain.item.soundsource.SoundSource;
import com.hoo.common.adapter.in.web.DateTimeFormatters;
import com.hoo.common.adapter.out.persistence.entity.SoundSourceJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class SoundSourceMapper {

    public SoundSource mapToDomainEntity(SoundSourceJpaEntity soundSourceJpaEntity) {
        return SoundSource.load(
                soundSourceJpaEntity.getId(),
                soundSourceJpaEntity.getItem().getId(),
                soundSourceJpaEntity.getAudioFileId(),
                soundSourceJpaEntity.getName(),
                soundSourceJpaEntity.getDescription(),
                soundSourceJpaEntity.getCreatedTime(),
                soundSourceJpaEntity.getUpdatedTime(),
                soundSourceJpaEntity.getIsActive()
        );
    }

    public QuerySoundSourceListResult.SoundSourceInfo mapToQuerySoundSourceListResult(SoundSourceJpaEntity soundSourceJpaEntity) {
        return new QuerySoundSourceListResult.SoundSourceInfo(
                soundSourceJpaEntity.getName(),
                soundSourceJpaEntity.getDescription(),
                DateTimeFormatters.DOT_DATE.getFormatter().format(soundSourceJpaEntity.getCreatedTime()),
                DateTimeFormatters.DOT_DATE.getFormatter().format(soundSourceJpaEntity.getUpdatedTime()),
                soundSourceJpaEntity.getIsActive(),
                soundSourceJpaEntity.getAudioFileId(),
                soundSourceJpaEntity.getItem().getHome().getUser().getNickname(),
                soundSourceJpaEntity.getItem().getHome().getUser().getEmail(),
                soundSourceJpaEntity.getItem().getHome().getUser().getId(),
                soundSourceJpaEntity.getItem().getHome().getName(),
                soundSourceJpaEntity.getItem().getHome().getId(),
                soundSourceJpaEntity.getItem().getRoom().getName(),
                soundSourceJpaEntity.getItem().getRoom().getId(),
                soundSourceJpaEntity.getItem().getName(),
                soundSourceJpaEntity.getItem().getId()
        );
    }
}
