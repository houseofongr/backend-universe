package com.hoo.universe.api.dto.command;

import com.hoo.common.internal.api.dto.FileCommand;
import com.hoo.universe.api.in.web.dto.command.CreateUniverseCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import static com.hoo.universe.test.dto.FileTestData.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class CreateUniverseCommandTest {

    InputStream mockInputStream = mock();

    private static CreateUniverseCommand.Metadata getMetadata() {
        CreateUniverseCommand.Metadata metadata = new CreateUniverseCommand.Metadata("우주", "유니버스는 우주입니다.", UUID.randomUUID(), UUID.randomUUID(), "PUBLIC", List.of());
        return metadata;
    }

    @Test
    @DisplayName("잘못된 요청 파라미터")
    void testBadCommand() {
        String nullTitle = null;
        String emptyTitle = "";
        String blankTitle = " ";
        String exceed5000 = "a".repeat(5001);
        List<String> exceedTagCount = List.of("소프트웨어개발", "백엔드개발", "프로그래밍", "자바개발", "웹개발", "데이터베이스설계", "도메인주도설계", "클린코드", "마이크로서비스아키텍처", "헥사고날아키텍처", "+1");
        List<String> exceedTagSize = List.of("a".repeat(501));

        assertThatThrownBy(() -> new CreateUniverseCommand.Metadata(nullTitle, "유니버스는 우주입니다.", UUID.randomUUID(), UUID.randomUUID(), "PUBLIC", List.of("우주", "행성", "지구", "별"))).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new CreateUniverseCommand.Metadata(emptyTitle, "유니버스는 우주입니다.", UUID.randomUUID(), UUID.randomUUID(), "PUBLIC", List.of("우주", "행성", "지구", "별"))).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new CreateUniverseCommand.Metadata(blankTitle, "유니버스는 우주입니다.", UUID.randomUUID(), UUID.randomUUID(), "PUBLIC", List.of("우주", "행성", "지구", "별"))).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new CreateUniverseCommand.Metadata("우주", exceed5000, UUID.randomUUID(), UUID.randomUUID(), "PUBLIC", List.of("우주", "행성", "지구", "별"))).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new CreateUniverseCommand.Metadata("우주", "유니버스는 우주입니다.", UUID.randomUUID(), UUID.randomUUID(), "PUBLIC", exceedTagSize)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new CreateUniverseCommand.Metadata("우주", "유니버스는 우주입니다.", UUID.randomUUID(), UUID.randomUUID(), "PUBLIC", exceedTagCount)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("썸네일, 썸뮤직, 내부이미지 파일 누락")
    void testFileOmissionOrDuplicateName() {
        // given
        CreateUniverseCommand.Metadata metadata = getMetadata();

        FileCommand thumbmusic = defaultAudioFileCommand();
        FileCommand thumbnail = defaultImageFileCommand();
        FileCommand background = defaultImageFileCommand();

        assertThatThrownBy(() -> new CreateUniverseCommand(metadata, null, thumbnail, background)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new CreateUniverseCommand(metadata, thumbmusic, null, background)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new CreateUniverseCommand(metadata, thumbmusic, thumbnail, null)).isInstanceOf(IllegalArgumentException.class);
    }
}