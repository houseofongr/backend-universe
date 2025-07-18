package com.hoo.universe.api.dto.command.category;

public record CreateCategoryCommand(
        String eng,
        String kor
) {
    public CreateCategoryCommand {
        if ((kor == null || kor.isBlank() || kor.length() > 100) ||
            (eng == null || eng.isBlank() || eng.length() > 100))
            throw new IllegalArgumentException();
    }
}
