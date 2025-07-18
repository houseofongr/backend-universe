package com.hoo.admin.application.service.category;

import com.hoo.admin.application.port.in.category.SearchCategoryResult;
import com.hoo.admin.application.port.in.category.SearchCategoryUseCase;
import com.hoo.admin.application.port.out.category.FindCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchCategoryService implements SearchCategoryUseCase {

    private final FindCategoryPort findCategoryPort;

    @Override
    public SearchCategoryResult search() {
        return findCategoryPort.findAll();
    }
}
