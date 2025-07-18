package com.hoo.universe.adapter.in.web.category;

import com.hoo.universe.api.dto.command.category.UpdateCategoryCommand;
import com.hoo.universe.api.dto.result.category.UpdateCategoryResult;
import com.hoo.universe.api.in.category.UpdateCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UpdateCategoryController {

    private final UpdateCategoryUseCase useCase;

    @PatchMapping("/universes/categories/{categoryID}")
    ResponseEntity<UpdateCategoryResult> update(@PathVariable UUID categoryID, @RequestBody UpdateCategoryCommand command) {
        return ResponseEntity.ok(useCase.updateCategory(categoryID, command));
    }
}
