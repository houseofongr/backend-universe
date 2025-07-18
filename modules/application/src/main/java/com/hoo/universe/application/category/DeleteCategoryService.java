package com.hoo.universe.application.category;

import com.hoo.universe.api.dto.result.category.DeleteCategoryResult;
import com.hoo.universe.api.in.category.DeleteCategoryUseCase;
import com.hoo.universe.api.out.persistence.CommandCategoryPort;
import com.hoo.universe.api.out.persistence.QueryCategoryPort;
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
    private final CommandCategoryPort commandCategoryPort;

    @Override
    public DeleteCategoryResult deleteCategory(UUID categoryID) {

        Category category = queryCategoryPort.findUniverseCategory(categoryID);
        commandCategoryPort.deleteCategory(categoryID);

        return new DeleteCategoryResult(
                category.getId(),
                category.getEng(),
                category.getKor()
        );
    }
}
