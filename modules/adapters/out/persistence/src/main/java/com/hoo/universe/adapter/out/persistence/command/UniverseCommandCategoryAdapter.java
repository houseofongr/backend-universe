package com.hoo.universe.adapter.out.persistence.command;

import com.hoo.universe.adapter.out.persistence.entity.CategoryJpaEntity;
import com.hoo.universe.adapter.out.persistence.repository.CategoryJpaRepository;
import com.hoo.universe.api.out.persistence.CommandCategoryPort;
import com.hoo.universe.application.exception.AdapterErrorCode;
import com.hoo.universe.application.exception.UniverseAdapterException;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UniverseCommandCategoryAdapter implements CommandCategoryPort {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public void saveNewCategory(UUID uuid, String kor, String eng) {
        CategoryJpaEntity newCategory = CategoryJpaEntity.create(uuid, kor, eng);
        categoryJpaRepository.save(newCategory);
    }

    @Override
    public void updateCategory(UUID categoryID, String kor, String eng) {
        CategoryJpaEntity categoryJpaEntity = categoryJpaRepository.findByUuid(categoryID)
                .orElseThrow(() -> new UniverseAdapterException(AdapterErrorCode.CATEGORY_NOT_FOUND));

        categoryJpaEntity.update(kor, eng);
    }

    @Override
    public void deleteCategory(UUID categoryID) {
        categoryJpaRepository.deleteByUuid(categoryID);
    }

}
