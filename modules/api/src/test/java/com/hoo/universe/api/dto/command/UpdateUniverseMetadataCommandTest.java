package com.hoo.universe.api.dto.command;

import com.hoo.common.enums.AccessLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class UpdateUniverseMetadataCommandTest {

    @Test
    @DisplayName("입력값 검증")
    void testTitleCondition() {
        String emptyTitle = "";
        String blankTitle = " ";
        String length100 = "a".repeat(101);
        String length5000 = "a".repeat(5001);
        List<String> tag11 = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11");
        List<String> tagLength500 = List.of("a".repeat(501));

        assertThatThrownBy(() -> new UpdateUniverseMetadataCommand(emptyTitle, null, null, null, null, null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new UpdateUniverseMetadataCommand(blankTitle, null, null, null, null, null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new UpdateUniverseMetadataCommand(length100, null, null, null, null, null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new UpdateUniverseMetadataCommand("우주", length5000, null, null, null, null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new UpdateUniverseMetadataCommand(null, null, null, null, null, tag11)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new UpdateUniverseMetadataCommand(null, null, null, null, null, tagLength500)).isInstanceOf(IllegalArgumentException.class);

        UpdateUniverseMetadataCommand command = new UpdateUniverseMetadataCommand("오르트구름", "오르트구름은 태양계 최외곽에 위치하고 있습니다.", UUID.randomUUID(), UUID.randomUUID(), AccessLevel.PRIVATE.name(), List.of("오르트구름", "태양계", "윤하", "별"));
        assertThat(command).isNotNull();
    }

}