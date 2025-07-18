package com.hoo.admin.adapter.in.web.category;

import com.hoo.admin.application.port.in.category.UpdateCategoryCommand;
import com.hoo.admin.application.port.in.category.UpdateCategoryResult;
import com.hoo.admin.application.port.in.category.UpdateCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatchCategoryController {

    private final UpdateCategoryUseCase useCase;

    @PatchMapping("/admin/categories/{categoryId}")
    ResponseEntity<UpdateCategoryResult> update(@PathVariable Long categoryId, @RequestBody UpdateCategoryCommand command) {
        return ResponseEntity.ok(useCase.update(categoryId, command));
    }
}
