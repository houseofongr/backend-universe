package com.hoo.universe.adapter.out.persistence.query;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.adapter.out.persistence.UniverseMapper;
import com.hoo.universe.adapter.out.persistence.entity.CategoryJpaEntity;
import com.hoo.universe.adapter.out.persistence.entity.PieceJpaEntity;
import com.hoo.universe.adapter.out.persistence.entity.SoundJpaEntity;
import com.hoo.universe.adapter.out.persistence.repository.CategoryJpaRepository;
import com.hoo.universe.adapter.out.persistence.repository.PieceJpaRepository;
import com.hoo.universe.adapter.out.persistence.repository.UniverseJpaRepository;
import com.hoo.universe.api.dto.command.SearchUniverseCommand;
import com.hoo.universe.api.dto.query.OpenPieceQueryResult;
import com.hoo.universe.api.dto.query.UniverseListQueryInfo;
import com.hoo.universe.api.dto.result.UniverseListInfo;
import com.hoo.universe.api.dto.result.piece.OpenPieceResult;
import com.hoo.universe.api.out.persistence.QueryCategoryPort;
import com.hoo.universe.api.out.persistence.QueryUniversePort;
import com.hoo.universe.application.exception.AdapterErrorCode;
import com.hoo.universe.application.exception.UniverseAdapterException;
import com.hoo.universe.domain.vo.Category;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class UniverseQueryAdapter implements
        QueryUniversePort,
        QueryCategoryPort {

    private final UniverseJpaRepository universeJpaRepository;
    private final PieceJpaRepository pieceJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final UniverseMapper universeMapper;

    @Override
    public PageQueryResult<UniverseListQueryInfo> searchUniverses(SearchUniverseCommand command) {
        return universeJpaRepository.searchUniverse(command.categoryID(), command.pageRequest()).map(universeMapper::mapToUniverseInfo);
    }

    @Override
    public OpenPieceQueryResult searchPiece(UUID pieceID, PageRequest pageRequest) {

        PieceJpaEntity pieceJpaEntity = pieceJpaRepository.findByUuid(pieceID)
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.PIECE_NOT_FOUND));

        PageQueryResult<SoundJpaEntity> soundQueryResult = pieceJpaRepository.searchAllSoundsById(pieceID, pageRequest);

        return universeMapper.mapToOpenPieceResult(pieceJpaEntity, soundQueryResult);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryJpaRepository.findAll().stream().map(universeMapper::mapToUniverseCategory).toList();
    }

    @Override
    public Category findUniverseCategory(UUID categoryId) {
        CategoryJpaEntity category = categoryJpaRepository.findByUuid(categoryId)
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.CATEGORY_NOT_FOUND));

        return universeMapper.mapToUniverseCategory(category);
    }

}
