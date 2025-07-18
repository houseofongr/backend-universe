package com.hoo.admin.adapter.out.persistence;

import com.hoo.admin.adapter.out.persistence.mapper.SoundSourceMapper;
import com.hoo.admin.application.port.in.soundsource.QuerySoundSourceListCommand;
import com.hoo.admin.application.port.in.soundsource.QuerySoundSourceListResult;
import com.hoo.admin.application.port.out.soundsource.*;
import com.hoo.admin.domain.item.soundsource.SoundSource;
import com.hoo.common.adapter.out.persistence.entity.ItemJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.SoundSourceJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.ItemJpaRepository;
import com.hoo.common.adapter.out.persistence.repository.SoundSourceJpaRepository;
import com.hoo.common.application.port.in.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SoundSourcePersistenceAdapter implements SaveSoundSourcePort, FindSoundSourcePort, UpdateSoundSourcePort, DeleteSoundSourcePort, QuerySoundSourcePort {

    private final ItemJpaRepository itemJpaRepository;
    private final SoundSourceJpaRepository soundSourceJpaRepository;
    private final SoundSourceMapper soundSourceMapper;

    @Override
    public Long saveSoundSource(SoundSource soundSource) {
        ItemJpaEntity itemJpaEntity = itemJpaRepository.findById(soundSource.getItemId().getId()).orElseThrow();

        SoundSourceJpaEntity soundSourceJpaEntity = SoundSourceJpaEntity.create(soundSource);
        soundSourceJpaRepository.save(soundSourceJpaEntity);

        soundSourceJpaEntity.setRelationship(itemJpaEntity);

        return soundSourceJpaEntity.getId();
    }

    @Override
    public Optional<SoundSource> loadSoundSource(Long id) {
        return soundSourceJpaRepository.findById(id)
                .map(soundSourceMapper::mapToDomainEntity);
    }

    @Override
    public void updateSoundSource(SoundSource soundSource) {
        SoundSourceJpaEntity soundSourceJpaEntity = soundSourceJpaRepository.findById(soundSource.getSoundSourceId().getId()).orElseThrow();
        soundSourceJpaEntity.update(soundSource);
    }

    @Override
    public void deleteSoundSource(SoundSource soundSource) {
        soundSourceJpaRepository.deleteById(soundSource.getSoundSourceId().getId());
    }

    @Override
    public QuerySoundSourceListResult querySoundSourceList(QuerySoundSourceListCommand command) {
        Page<QuerySoundSourceListResult.SoundSourceInfo> result = soundSourceJpaRepository.findAllWithRelatedEntity(command).map(soundSourceMapper::mapToQuerySoundSourceListResult);
        return new QuerySoundSourceListResult(result.getContent(), Pagination.of(result));
    }
}
