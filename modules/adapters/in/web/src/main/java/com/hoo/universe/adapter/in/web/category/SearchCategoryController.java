package com.hoo.universe.adapter.in.web.category;

import com.hoo.universe.api.in.web.dto.result.SearchCategoryResult;
import com.hoo.universe.api.in.web.usecase.SearchCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchCategoryController {

    private final SearchCategoryUseCase useCase;

    @GetMapping("/universes/categories")
    ResponseEntity<SearchCategoryResult> search() {
        return ResponseEntity.ok(useCase.searchAllCategories());
    }
}
