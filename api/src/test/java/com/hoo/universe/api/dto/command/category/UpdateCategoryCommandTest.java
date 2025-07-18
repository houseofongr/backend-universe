package com.hoo.universe.api.dto.command.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UpdateCategoryCommandTest {

    @Test
    @DisplayName("입력값 검증")
    void testVerify() {
        String nullName = null;
        String empty = "";
        String blank = " ";
        String enough = "a".repeat(100);
        String tooLong = "a".repeat(101);
        assertThatThrownBy(() -> new UpdateCategoryCommand(nullName, enough)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new UpdateCategoryCommand(empty, enough)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new UpdateCategoryCommand(blank, enough)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new UpdateCategoryCommand(tooLong, enough)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new UpdateCategoryCommand(enough, nullName)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new UpdateCategoryCommand(enough, empty)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new UpdateCategoryCommand(enough, blank)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new UpdateCategoryCommand(enough, tooLong)).isInstanceOf(IllegalArgumentException.class);
    }

}