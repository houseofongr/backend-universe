package com.hoo.admin.adapter.in.web.category;

import com.hoo.admin.application.port.in.category.DeleteCategoryResult;
import com.hoo.admin.application.port.in.category.DeleteCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteCategoryController {

    private final DeleteCategoryUseCase useCase;

    @DeleteMapping("/admin/categories/{categoryId}")
    ResponseEntity<DeleteCategoryResult> delete(@PathVariable Long categoryId) {
        return ResponseEntity.ok(useCase.delete(categoryId));
    }
}
