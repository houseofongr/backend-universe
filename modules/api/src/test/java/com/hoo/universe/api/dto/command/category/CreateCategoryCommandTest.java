package com.hoo.universe.api.dto.command.category;

import com.hoo.universe.api.in.web.dto.command.CreateCategoryCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateCategoryCommandTest {

    @Test
    @DisplayName("입력값 검증")
    void testVerify() {
        String nullName = null;
        String empty = "";
        String blank = " ";
        String enough = "a".repeat(100);
        String tooLong = "a".repeat(101);
        assertThatThrownBy(() -> new CreateCategoryCommand(nullName, enough)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new CreateCategoryCommand(empty, enough)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new CreateCategoryCommand(blank, enough)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new CreateCategoryCommand(tooLong, enough)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new CreateCategoryCommand(enough, nullName)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new CreateCategoryCommand(enough, empty)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new CreateCategoryCommand(enough, blank)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new CreateCategoryCommand(enough, tooLong)).isInstanceOf(IllegalArgumentException.class);
    }
}