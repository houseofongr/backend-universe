package com.hoo.admin.adapter.out.persistence;

import com.hoo.admin.adapter.out.persistence.mapper.SoundMapper;
import com.hoo.admin.application.port.out.sound.DeleteSoundPort;
import com.hoo.admin.application.port.out.sound.FindSoundPort;
import com.hoo.admin.application.port.out.sound.SaveSoundPort;
import com.hoo.admin.application.port.out.sound.UpdateSoundPort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.universe.piece.sound.Sound;
import com.hoo.common.adapter.out.persistence.entity.PieceJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.SoundJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.PieceJpaRepository;
import com.hoo.common.adapter.out.persistence.repository.SoundJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SoundPersistenceAdapter implements SaveSoundPort, FindSoundPort, UpdateSoundPort, DeleteSoundPort {

    private final PieceJpaRepository pieceJpaRepository;
    private final SoundJpaRepository soundJpaRepository;
    private final SoundMapper soundMapper;

    @Override
    public Long save(Sound sound) {
        Long pieceId = sound.getBasicInfo().getPieceId();
        PieceJpaEntity pieceJpaEntity = pieceJpaRepository.findById(pieceId).orElseThrow(() -> new AdminException(AdminErrorCode.PIECE_NOT_FOUND));
        SoundJpaEntity soundJpaEntity = SoundJpaEntity.create(sound, pieceJpaEntity);

        soundJpaRepository.save(soundJpaEntity);

        return soundJpaEntity.getId();
    }

    @Override
    public Sound find(Long id) {
        SoundJpaEntity soundJpaEntity = soundJpaRepository.findById(id).orElseThrow(() -> new AdminException(AdminErrorCode.SOUND_NOT_FOUND));
        return soundMapper.mapToDomainEntity(soundJpaEntity);
    }

    @Override
    public void update(Sound sound) {
        SoundJpaEntity soundJpaEntity = soundJpaRepository.findById(sound.getId()).orElseThrow(() -> new AdminException(AdminErrorCode.SOUND_NOT_FOUND));
        soundJpaEntity.update(sound);
    }

    @Override
    public void delete(Long id) {
        soundJpaRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<Long> ids) {
        soundJpaRepository.deleteAllById(ids);
    }
}
