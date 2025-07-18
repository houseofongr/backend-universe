package com.hoo.admin.adapter.in.web.category;

import com.hoo.admin.application.port.in.category.SearchCategoryResult;
import com.hoo.admin.application.port.in.category.SearchCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetCategoryController {

    private final SearchCategoryUseCase useCase;

    @GetMapping("/admin/categories")
    ResponseEntity<SearchCategoryResult> search() {
        return ResponseEntity.ok(useCase.search());
    }
}
