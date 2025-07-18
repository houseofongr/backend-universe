package com.hoo.admin.adapter.in.web.category;

import com.hoo.admin.application.port.in.category.CreateCategoryCommand;
import com.hoo.admin.application.port.in.category.CreateCategoryResult;
import com.hoo.admin.application.port.in.category.CreateCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostCategoryController {

    private final CreateCategoryUseCase useCase;

    @PostMapping("/admin/categories")
    ResponseEntity<CreateCategoryResult> create(@RequestBody CreateCategoryCommand command) {
        return new ResponseEntity<>(useCase.create(command), HttpStatus.CREATED);
    }
}
