package com.hoo.universe.api.in.dto;

public record UpdateCategoryCommand(
        String kor,
        String eng
) {
    public UpdateCategoryCommand {
        if ((kor == null || kor.isBlank() || kor.length() > 100) ||
            (eng == null || eng.isBlank() || eng.length() > 100))
            throw new IllegalArgumentException();
    }
}
