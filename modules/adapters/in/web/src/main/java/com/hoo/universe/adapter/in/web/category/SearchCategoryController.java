package com.hoo.universe.adapter.in.web.category;

import com.hoo.universe.api.dto.result.category.SearchCategoryResult;
import com.hoo.universe.api.in.category.SearchCategoryUseCase;
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
