package com.hoo.aar.adapter.out.persistence;

import com.hoo.aar.adapter.out.persistence.mapper.UniverseMapper;
import com.hoo.aar.application.port.in.universe.SearchPublicUniverseCommand;
import com.hoo.aar.application.port.in.universe.SearchPublicUniverseResult;
import com.hoo.aar.application.port.out.persistence.universe.CheckIsLikedUniversePort;
import com.hoo.aar.application.port.out.persistence.universe.SearchPublicUniversePort;
import com.hoo.aar.application.port.out.persistence.universe.ViewPublicUniversePort;
import com.hoo.aar.application.service.AarErrorCode;
import com.hoo.aar.application.service.AarException;
import com.hoo.admin.domain.universe.TraversalComponents;
import com.hoo.common.adapter.out.persistence.repository.UniverseJpaRepository;
import com.hoo.common.application.port.in.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component("AARUniversePersistenceAdapter")
@RequiredArgsConstructor
public class UniversePersistenceAdapter implements SearchPublicUniversePort, CheckIsLikedUniversePort, ViewPublicUniversePort {

    private final UniverseMapper universeMapper;
    private final UniverseJpaRepository universeJpaRepository;

    @Override
    public SearchPublicUniverseResult searchPublicUniverse(SearchPublicUniverseCommand command) {
        Page<SearchPublicUniverseResult.UniverseListInfo> entityPage = universeJpaRepository.searchAllPublic(command)
                .map(universeJpaEntity -> universeMapper.mapToSearchPublicUniverseListInfo(command.userId(), universeJpaEntity));

        return new SearchPublicUniverseResult(entityPage.getContent(), Pagination.of(entityPage));
    }

    @Override
    public boolean checkIsLiked(Long universeId, Long userId) {
        return universeJpaRepository.checkIsLiked(universeId, userId);
    }

    @Override
    public TraversalComponents viewPublicUniverse(Long universeId) {
        universeJpaRepository.findById(universeId).orElseThrow(() -> new AarException(AarErrorCode.UNIVERSE_NOT_FOUND)).view();

        return universeMapper.mapToTraversalComponents(universeJpaRepository.findAllPublicTreeComponentById(universeId));
    }
}
