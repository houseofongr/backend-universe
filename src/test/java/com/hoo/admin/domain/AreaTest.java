package com.hoo.admin.domain;

import com.hoo.admin.domain.exception.AreaLimitExceededException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AreaTest {

    @Test
    @DisplayName("영역 범위 테스트")
    void testAreaBoundary() throws AreaLimitExceededException {

        assertThatThrownBy(() -> new Area(32768F, 32768F)).isInstanceOf(AreaLimitExceededException.class);
        assertThatThrownBy(() -> new Area(32767F, 32768F)).isInstanceOf(AreaLimitExceededException.class);
        assertThatThrownBy(() -> new Area(32768F, 32767F)).isInstanceOf(AreaLimitExceededException.class);

        Area area = new Area(32767F, 32767F);

        assertThatThrownBy(() -> new Area(0F, 0F)).isInstanceOf(AreaLimitExceededException.class);
        assertThatThrownBy(() -> new Area(1F, 0F)).isInstanceOf(AreaLimitExceededException.class);
        assertThatThrownBy(() -> new Area(0F, 1F)).isInstanceOf(AreaLimitExceededException.class);

        area = new Area(1F, 1F);
    }

    @Test
    @DisplayName("영역 Getter 테스트")
    void testGetter() throws AreaLimitExceededException {
        Area area = new Area(32766F, 32767F);
        assertThat(area.getWidth()).isEqualTo(32766);
        assertThat(area.getHeight()).isEqualTo(32767F);
    }

    @Test
    @DisplayName("영역 부동소수점 허용 테스트")
    void testFloat() throws AreaLimitExceededException {
        Area area = new Area(32765.12F, 32766.23F);
        assertThat(area.getWidth()).isEqualTo(32765.12f);
        assertThat(area.getHeight()).isEqualTo(32766.23f);
    }

    @Test
    @DisplayName("영역 업데이트 테스트 - 현재 사용하지 않음")
    void testUpdate() throws AreaLimitExceededException {
        // given
        Area area = new Area(5000F, 5000F);

        // when
        area.update(4500F, null);

        // then
        assertThat(area.getWidth()).isEqualTo(4500F);
        assertThat(area.getHeight()).isEqualTo(5000F);

        assertThatThrownBy(() -> area.update(32767.1F, null))
                .isInstanceOf(AreaLimitExceededException.class);
        assertThatThrownBy(() -> area.update(0F, null))
                .isInstanceOf(AreaLimitExceededException.class);
    }

}