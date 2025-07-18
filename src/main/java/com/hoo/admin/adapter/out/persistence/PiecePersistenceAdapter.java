package com.hoo.admin.adapter.out.persistence;

import com.hoo.admin.adapter.out.persistence.mapper.PieceMapper;
import com.hoo.admin.application.port.in.piece.SearchPieceCommand;
import com.hoo.admin.application.port.in.piece.SearchPieceResult;
import com.hoo.admin.application.port.out.piece.DeletePiecePort;
import com.hoo.admin.application.port.out.piece.FindPiecePort;
import com.hoo.admin.application.port.out.piece.SavePiecePort;
import com.hoo.admin.application.port.out.piece.UpdatePiecePort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.universe.piece.Piece;
import com.hoo.common.adapter.out.persistence.entity.PieceJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.PieceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PiecePersistenceAdapter implements SavePiecePort, FindPiecePort, UpdatePiecePort, DeletePiecePort {

    private final PieceJpaRepository pieceJpaRepository;
    private final PieceMapper pieceMapper;

    @Override
    public Long save(Piece piece) {
        PieceJpaEntity pieceJpaEntity = PieceJpaEntity.create(piece);
        pieceJpaRepository.save(pieceJpaEntity);

        return pieceJpaEntity.getId();
    }

    @Override
    public Piece find(Long id) {
        PieceJpaEntity pieceJpaEntity = pieceJpaRepository.findById(id).orElseThrow(() -> new AdminException(AdminErrorCode.PIECE_NOT_FOUND));
        return pieceMapper.mapToSingleDomainEntity(pieceJpaEntity);
    }

    @Override
    public Piece findWithSounds(Long id) {
        PieceJpaEntity pieceJpaEntity = pieceJpaRepository.findById(id).orElseThrow(() -> new AdminException(AdminErrorCode.PIECE_NOT_FOUND));
        return pieceMapper.mapToPieceWithSounds(pieceJpaEntity);
    }

    @Override
    public SearchPieceResult search(SearchPieceCommand command) {
        PieceJpaEntity pieceJpaEntity = pieceJpaRepository.findById(command.pieceId()).orElseThrow(() -> new AdminException(AdminErrorCode.PIECE_NOT_FOUND));
        return pieceMapper.mapToSearchPieceResult(pieceJpaEntity, pieceJpaRepository.searchAll(command));
    }

    @Override
    public void update(Long pieceId, Piece piece) {
        PieceJpaEntity pieceJpaEntity = pieceJpaRepository.findById(pieceId).orElseThrow(() -> new AdminException(AdminErrorCode.PIECE_NOT_FOUND));
        pieceJpaEntity.update(piece);
    }

    @Override
    public void delete(Long id) {
        pieceJpaRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<Long> ids) {
        pieceJpaRepository.deleteAllById(ids);
    }
}
