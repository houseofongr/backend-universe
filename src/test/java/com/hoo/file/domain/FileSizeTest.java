package com.hoo.file.domain;

import com.hoo.file.domain.exception.FileSizeLimitExceedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class FileSizeTest {

    Long limit = 100 * 1024 * 1024L;

    @Test
    @DisplayName("Byte 단위 변환")
    void testGetByte() throws FileSizeLimitExceedException {
        // given
        FileSize _999ByteSize = new FileSize(999L, limit);

        // when
        String _999Byte = _999ByteSize.getUnitSize();

        // then
        assertThat(_999Byte).isEqualTo("999Byte");
    }

    @Test
    @DisplayName("KB 단위 변환")
    void testGetKBSize() throws FileSizeLimitExceedException {
        // given
        FileSize _1KBSize = new FileSize(1001L, limit);
        FileSize _1KBSize2 = new FileSize(1024L, limit);
        FileSize _999KBSize = new FileSize(1024 * 999L, limit);

        // when
        String _1KB = _1KBSize.getUnitSize();
        String _1KB2 = _1KBSize2.getUnitSize();
        String _999KB = _999KBSize.getUnitSize();

        // then
        assertThat(_1KB).isEqualTo("0.98KB");
        assertThat(_1KB2).isEqualTo("1.00KB");
        assertThat(_999KB).isEqualTo("999.00KB");
    }

    @Test
    @DisplayName("MB 단위 변환")
    void testGetMBSize() throws FileSizeLimitExceedException {
        // given
        FileSize _1MBSize = new FileSize(1024 * 1000L, limit);

        // when
        String _1MB = _1MBSize.getUnitSize();

        // then
        assertThat(_1MB).isEqualTo("0.98MB");
    }

    @Test
    @DisplayName("파일용량 초과")
    void testFileSizeExceed() throws FileSizeLimitExceedException {
        assertThat(new FileSize(100 * 1024 * 1024L, limit).getUnitSize())
                .isEqualTo("100.00MB");
        assertThatThrownBy(() -> new FileSize(100 * 1024 * 1024L + 1, limit))
                .isInstanceOf(FileSizeLimitExceedException.class);
    }
}