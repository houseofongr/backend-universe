package com.hoo.common.adapter.in.web;

import jakarta.validation.ConstraintViolation;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Set;

public record ValidationErrorResponse(
        String fieldName,
        Object rejectedValue,
        String message
) {

    public static ValidationErrorResponse of(BindingResult bindingResult) {
        return new ValidationErrorResponse(
                bindingResult.getFieldError().getField(),
                bindingResult.getFieldError().getRejectedValue(),
                bindingResult.getFieldError().getDefaultMessage()
        );
    }

    public static List<ValidationErrorResponse> of(Set<ConstraintViolation<?>> constraintViolations) {

        return constraintViolations.stream().map(violation ->
                new ValidationErrorResponse(
                        violation.getPropertyPath().toString(),
                        violation.getInvalidValue(),
                        violation.getMessage()
                )
        ).toList();
    }
}
