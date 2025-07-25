package com.hoo.universe.application;

import com.hoo.universe.api.in.dto.DeleteCategoryResult;
import com.hoo.universe.api.in.DeleteCategoryUseCase;
import com.hoo.universe.api.out.UpdateCategoryPort;
import com.hoo.universe.api.out.QueryCategoryPort;
import com.hoo.universe.domain.vo.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteCategoryService implements DeleteCategoryUseCase {

    private final QueryCategoryPort queryCategoryPort;
    private final UpdateCategoryPort updateCategoryPort;

    @Override
    public DeleteCategoryResult deleteCategory(UUID categoryID) {
        Category category = queryCategoryPort.findUniverseCategory(categoryID);
        updateCategoryPort.deleteCategory(categoryID);

        return new DeleteCategoryResult(
                category.getId(),
                category.getEng(),
                category.getKor()
        );
    }
}
