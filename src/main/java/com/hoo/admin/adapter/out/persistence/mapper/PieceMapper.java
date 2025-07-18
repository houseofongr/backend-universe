package com.hoo.admin.adapter.out.persistence.mapper;

import com.hoo.admin.application.port.in.piece.SearchPieceResult;
import com.hoo.admin.domain.universe.piece.Piece;
import com.hoo.admin.domain.universe.piece.sound.Sound;
import com.hoo.common.adapter.out.persistence.entity.PieceJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.SoundJpaEntity;
import com.hoo.common.application.port.in.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PieceMapper {

    public Piece mapToSingleDomainEntity(PieceJpaEntity pieceJpaEntity) {
        return Piece.loadWithoutRelation(pieceJpaEntity.getId(),
                pieceJpaEntity.getInnerImageFileId(),
                pieceJpaEntity.getTitle(),
                pieceJpaEntity.getDescription(),
                pieceJpaEntity.getSx(),
                pieceJpaEntity.getSy(),
                pieceJpaEntity.getEx(),
                pieceJpaEntity.getEy(),
                pieceJpaEntity.getHidden(),
                pieceJpaEntity.getCreatedTime(),
                pieceJpaEntity.getUpdatedTime()
        );
    }

    public SearchPieceResult mapToSearchPieceResult(PieceJpaEntity piece, Page<SoundJpaEntity> entityPage) {
        List<SoundJpaEntity> content = entityPage.getContent();
        return new SearchPieceResult(
                piece.getId(),
                piece.getTitle(),
                piece.getDescription(),
                piece.getHidden(),
                piece.getCreatedTime().toEpochSecond(),
                piece.getUpdatedTime().toEpochSecond(),
                content.stream().map(soundJpaEntity -> new SearchPieceResult.SoundInfo(
                        soundJpaEntity.getId(),
                        soundJpaEntity.getAudioFileId(),
                        soundJpaEntity.getTitle(),
                        soundJpaEntity.getDescription(),
                        soundJpaEntity.getHidden(),
                        soundJpaEntity.getCreatedTime().toEpochSecond(),
                        soundJpaEntity.getUpdatedTime().toEpochSecond())).toList(),
                Pagination.of(entityPage)
        );
    }

    public Piece mapToPieceWithSounds(PieceJpaEntity pieceJpaEntity) {
        return Piece.loadWithSound(pieceJpaEntity.getId(),
                pieceJpaEntity.getInnerImageFileId(),
                pieceJpaEntity.getSounds().stream().map(soundJpaEntity ->
                        Sound.loadFile(soundJpaEntity.getId(),
                                soundJpaEntity.getAudioFileId())
                ).toList()
        );
    }
}
