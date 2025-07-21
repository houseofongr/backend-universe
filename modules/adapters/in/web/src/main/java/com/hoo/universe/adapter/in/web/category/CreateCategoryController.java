package com.hoo.universe.adapter.in.web.category;

import com.hoo.universe.api.in.dto.CreateCategoryCommand;
import com.hoo.universe.api.in.dto.CreateCategoryResult;
import com.hoo.universe.api.in.CreateCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreateCategoryController {

    private final CreateCategoryUseCase useCase;

    @PostMapping("/universes/categories")
    ResponseEntity<CreateCategoryResult> create(@RequestBody CreateCategoryCommand command) {
        return new ResponseEntity<>(useCase.createNewCategory(command), HttpStatus.CREATED);
    }
}
