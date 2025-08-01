package com.hoo.universe.application;

import com.hoo.universe.api.in.dto.SearchCategoryResult;
import com.hoo.universe.api.in.SearchCategoryUseCase;
import com.hoo.universe.api.out.QueryCategoryPort;
import com.hoo.universe.domain.vo.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchCategoryService implements SearchCategoryUseCase {

    private final QueryCategoryPort queryCategoryPort;

    @Override
    public SearchCategoryResult searchAllCategories() {
        List<Category> allCategories = queryCategoryPort.findAllCategories();
        return new SearchCategoryResult(allCategories);
    }
}
