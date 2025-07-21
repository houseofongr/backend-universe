package com.hoo.universe.adapter.out.persistence.command;

import com.hoo.universe.adapter.out.persistence.OutlineSerializer;
import com.hoo.universe.adapter.out.persistence.entity.PieceJpaEntity;
import com.hoo.universe.adapter.out.persistence.entity.UniverseJpaEntity;
import com.hoo.universe.application.exception.AdapterErrorCode;
import com.hoo.universe.application.exception.UniverseAdapterException;
import com.hoo.universe.adapter.out.persistence.repository.PieceJpaRepository;
import com.hoo.universe.adapter.out.persistence.repository.SoundJpaRepository;
import com.hoo.universe.adapter.out.persistence.repository.UniverseJpaRepository;
import com.hoo.universe.api.out.HandlePieceEventPort;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Sound;
import com.hoo.universe.domain.event.piece.PieceCreateEvent;
import com.hoo.universe.domain.event.piece.PieceDeleteEvent;
import com.hoo.universe.domain.event.piece.PieceMetadataUpdateEvent;
import com.hoo.universe.domain.event.piece.PieceMoveEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PieceEventHandleAdapter implements HandlePieceEventPort {

    private final UniverseJpaRepository universeJpaRepository;
    private final PieceJpaRepository pieceJpaRepository;
    private final SoundJpaRepository soundJpaRepository;
    private final OutlineSerializer outlineSerializer;

    @Override
    public void handlePieceCreateEvent(PieceCreateEvent event) {

        Piece newPiece = event.newPiece();
        UniverseJpaEntity universeJpaEntity = universeJpaRepository.findByUuid(newPiece.getUniverseID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        String outlinePoints = outlineSerializer.serialize(newPiece.getOutline());
        PieceJpaEntity pieceJpaEntity = PieceJpaEntity.createNewEntity(newPiece, universeJpaEntity, outlinePoints);

        pieceJpaRepository.save(pieceJpaEntity);
    }

    @Override
    public void handlePieceMetadataUpdateEvent(PieceMetadataUpdateEvent event) {

        PieceJpaEntity pieceJpaEntity = pieceJpaRepository.findByUuid(event.pieceID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        pieceJpaEntity.applyEvent(event);
    }

    @Override
    public void handlePieceMoveEvent(PieceMoveEvent event) {

        PieceJpaEntity pieceJpaEntity = pieceJpaRepository.findByUuid(event.pieceID().uuid())
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.LOAD_ENTITY_FAILED_BY_DOMAIN_ID));

        String outlinePoints = outlineSerializer.serialize(event.outline());

        pieceJpaEntity.move(outlinePoints);
    }

    @Override
    public void handlePieceDeleteEvent(PieceDeleteEvent event) {

        pieceJpaRepository.deleteByUuid(event.deletePieceID().uuid());
        soundJpaRepository.deleteAllByUuidIn(event.deleteSoundIDs().stream().map(Sound.SoundID::uuid).toList());
    }
}
