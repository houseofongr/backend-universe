package com.hoo.universe.adapter.in.web.category;

import com.hoo.universe.api.dto.result.category.DeleteCategoryResult;
import com.hoo.universe.api.in.category.DeleteCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DeleteCategoryController {

    private final DeleteCategoryUseCase useCase;

    @DeleteMapping("/universes/categories/{categoryID}")
    ResponseEntity<DeleteCategoryResult> delete(@PathVariable UUID categoryID) {
        return ResponseEntity.ok(useCase.deleteCategory(categoryID));
    }
}
