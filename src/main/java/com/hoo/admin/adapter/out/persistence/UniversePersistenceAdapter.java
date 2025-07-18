package com.hoo.admin.adapter.out.persistence;

import com.hoo.common.adapter.out.persistence.repository.CategoryJpaRepository;
import com.hoo.common.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.admin.adapter.out.persistence.mapper.UniverseMapper;
import com.hoo.admin.application.port.in.universe.SearchUniverseCommand;
import com.hoo.admin.application.port.in.universe.SearchUniverseResult;
import com.hoo.admin.application.port.in.universe.UpdateUniverseResult;
import com.hoo.admin.application.port.out.universe.DeleteUniversePort;
import com.hoo.admin.application.port.out.universe.FindUniversePort;
import com.hoo.admin.application.port.out.universe.SaveUniversePort;
import com.hoo.admin.application.port.out.universe.UpdateUniversePort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.universe.TraversalComponents;
import com.hoo.admin.domain.universe.Universe;
import com.hoo.common.adapter.out.persistence.entity.*;
import com.hoo.common.adapter.out.persistence.repository.HashtagJpaRepository;
import com.hoo.common.adapter.out.persistence.repository.UniverseJpaRepository;
import com.hoo.common.application.port.in.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UniversePersistenceAdapter implements SaveUniversePort, FindUniversePort, UpdateUniversePort, DeleteUniversePort {

    private final HashtagJpaRepository hashtagJpaRepository;
    private final UniverseJpaRepository universeJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final UniverseMapper universeMapper;

    //TODO : 여러 태그 한번에 조회하도록 로직 수정(현재 쿼리가 tag 개수만큼 나감)
    public HashtagJpaEntity getOrCreate(String tag) {
        return hashtagJpaRepository.findByTag(tag)
                .orElseGet(() -> hashtagJpaRepository.save(HashtagJpaEntity.create(tag)));
    }

    @Override
    public Universe save(Universe universe) {
        UserJpaEntity author = userJpaRepository.findById(universe.getAuthorInfo().getId()).orElseThrow(() -> new AdminException(AdminErrorCode.USER_NOT_FOUND));
        CategoryJpaEntity category = categoryJpaRepository.findById(universe.getCategory().getId()).orElseThrow(() -> new AdminException(AdminErrorCode.CATEGORY_NOT_FOUND));

        UniverseJpaEntity universeJpaEntity = UniverseJpaEntity.create(universe, author, category);

        for (String tag : universe.getSocialInfo().getHashtags())
            universeJpaEntity.getUniverseHashtags().add(UniverseHashtagJpaEntity.create(universeJpaEntity, getOrCreate(tag)));

        universeJpaRepository.save(universeJpaEntity);

        return universeMapper.mapToDomainEntity(universeJpaEntity);
    }

    @Override
    public Universe load(Long id) {
        return universeMapper.mapToDomainEntity(universeJpaRepository.findById(id)
                .orElseThrow(() -> new AdminException(AdminErrorCode.UNIVERSE_NOT_FOUND)));
    }

    @Override
    public SearchUniverseResult search(SearchUniverseCommand command) {
        Page<SearchUniverseResult.UniverseListInfo> entityPage = universeJpaRepository.searchAll(command).map(universeMapper::mapToSearchUniverseListInfo);
        return new SearchUniverseResult(entityPage.getContent(), Pagination.of(entityPage));
    }

    @Override
    public SearchUniverseResult.UniverseDetailInfo find(Long id) {
        return universeMapper.mapToSearchUniverseDetailInfo(universeJpaRepository.findById(id)
                .orElseThrow(() -> new AdminException(AdminErrorCode.UNIVERSE_NOT_FOUND)));
    }

    @Override
    public TraversalComponents findTreeComponents(Long id) {
        TraversalJpaEntityComponents components = universeJpaRepository.findAllTreeComponentById(id);
        return universeMapper.mapToTraversalComponent(components.universeJpaEntity(), components.spaceJpaEntities(), components.pieceJpaEntities());
    }

    @Override
    public UpdateUniverseResult.Detail updateDetail(Universe universe) {
        UniverseJpaEntity targetEntity = universeJpaRepository.findById(universe.getId()).orElseThrow(() -> new AdminException(AdminErrorCode.UNIVERSE_NOT_FOUND));
        targetEntity.update(universe);

        // Author Update
        if (!Objects.equals(targetEntity.getAuthor().getId(), universe.getAuthorInfo().getId())) {
            UserJpaEntity userJpaEntity = userJpaRepository.findById(universe.getAuthorInfo().getId()).orElseThrow(() -> new AdminException(AdminErrorCode.USER_NOT_FOUND));
            targetEntity.updateAuthor(userJpaEntity);
        }

        // Category Update
        if (!Objects.equals(targetEntity.getCategory().getId(), universe.getCategory().getId())) {
            CategoryJpaEntity categoryJpaEntity = categoryJpaRepository.findById(universe.getCategory().getId()).orElseThrow(() -> new AdminException(AdminErrorCode.CATEGORY_NOT_FOUND));
            targetEntity.updateCategory(categoryJpaEntity);
        }

        // Hashtag Update
        List<UniverseHashtagJpaEntity> universeHashtags = targetEntity.getUniverseHashtags();
        Set<String> notExistTags = new HashSet<>(universe.getSocialInfo().getHashtags());
        for (int i = universeHashtags.size() - 1; i >= 0; i--) {
            boolean exist = false;
            for (String tag : universe.getSocialInfo().getHashtags()) {
                if (universeHashtags.get(i).getHashtag().getTag().equalsIgnoreCase(tag)) {
                    notExistTags.remove(tag);
                    exist = true;
                    break;
                }
            }
            if (!exist) universeHashtags.remove(i);
        }

        for (String tag : notExistTags) {
            universeHashtags.add(UniverseHashtagJpaEntity.create(targetEntity, getOrCreate(tag)));
        }

        return universeMapper.mapToUpdateUniverseDetailResult(targetEntity);
    }

    @Override
    public void updateThumbMusic(Universe universe) {
        UniverseJpaEntity targetEntity = universeJpaRepository.findById(universe.getId()).orElseThrow(() -> new AdminException(AdminErrorCode.UNIVERSE_NOT_FOUND));
        targetEntity.update(universe);
    }

    @Override
    public void updateThumbnail(Universe universe) {
        UniverseJpaEntity targetEntity = universeJpaRepository.findById(universe.getId()).orElseThrow(() -> new AdminException(AdminErrorCode.UNIVERSE_NOT_FOUND));
        targetEntity.update(universe);
    }

    @Override
    public void updateInnerImage(Universe universe) {
        UniverseJpaEntity targetEntity = universeJpaRepository.findById(universe.getId()).orElseThrow(() -> new AdminException(AdminErrorCode.UNIVERSE_NOT_FOUND));
        targetEntity.update(universe);
    }

    @Override
    public void delete(Long id) {
        UniverseJpaEntity targetEntity = universeJpaRepository.findById(id).orElseThrow();
        universeJpaRepository.delete(targetEntity);
    }
}
