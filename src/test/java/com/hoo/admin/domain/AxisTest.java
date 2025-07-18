package com.hoo.admin.domain;

import com.hoo.admin.domain.exception.AxisLimitExceededException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AxisTest {

    @Test
    @DisplayName("좌표 범위 테스트")
    void testAxisBoundary() throws AxisLimitExceededException {
        assertThatThrownBy(() -> new Axis(-1F, 0F, 0F)).isInstanceOf(AxisLimitExceededException.class);
        assertThatThrownBy(() -> new Axis(0F, -1F, 0F)).isInstanceOf(AxisLimitExceededException.class);
        assertThatThrownBy(() -> new Axis(0F, 0F, -1F)).isInstanceOf(AxisLimitExceededException.class);

        new Axis(0F, 0F, 0F);

        assertThatThrownBy(() -> new Axis(32768F, 32767F, 32767F)).isInstanceOf(AxisLimitExceededException.class);
        assertThatThrownBy(() -> new Axis(32767F, 32768F, 32767F)).isInstanceOf(AxisLimitExceededException.class);
        assertThatThrownBy(() -> new Axis(32767F, 32767F, 32768F)).isInstanceOf(AxisLimitExceededException.class);

        Axis axis1 = new Axis(32765F, 32766F, 32767F);
        assertThat(axis1.getX()).isEqualTo(32765);
        assertThat(axis1.getY()).isEqualTo(32766);
        assertThat(axis1.getZ()).isEqualTo(32767);

        assertThatThrownBy(() -> Axis.from(-1F, 0F)).isInstanceOf(AxisLimitExceededException.class);
        assertThatThrownBy(() -> Axis.from(0F, -1F)).isInstanceOf(AxisLimitExceededException.class);

        Axis.from(0F, 0F);

        assertThatThrownBy(() -> Axis.from(32767F, 32768F)).isInstanceOf(AxisLimitExceededException.class);
        assertThatThrownBy(() -> Axis.from(32768F, 32767F)).isInstanceOf(AxisLimitExceededException.class);

        Axis axis2 = Axis.from(32767F, 32767F);

        assertThat(axis2.getZ()).isEqualTo(0f);
    }

    @Test
    @DisplayName("좌표 Getter 테스트")
    void testGetter() throws AxisLimitExceededException {
        Axis axis1 = new Axis(32765F, 32766F, 32767F);
        assertThat(axis1.getX()).isEqualTo(32765);
        assertThat(axis1.getY()).isEqualTo(32766);
        assertThat(axis1.getZ()).isEqualTo(32767);
    }

    @Test
    @DisplayName("소수점 허용 테스트")
    void testAllowFloat() throws AxisLimitExceededException {
        Axis axis = new Axis(32765.12F, 32766.23F, 32766.34F);
        assertThat(axis.getX()).isEqualTo(32765.12f);
        assertThat(axis.getY()).isEqualTo(32766.23f);
        assertThat(axis.getZ()).isEqualTo(32766.34f);
    }

    @Test
    @DisplayName("좌표 수정 테스트 - 현재 사용하지 않음")
    void testUpdate() throws AxisLimitExceededException {
        // given
        Axis axis = new Axis(123F, 456F, 1F);

        Float x = 345f;
        Float y = 567f;
        Float z = null;

        // when
        axis.update(x, y, z);

        // then
        assertThat(axis.getX()).isEqualTo(x);
        assertThat(axis.getY()).isEqualTo(y);
        assertThat(axis.getZ()).isEqualTo(1F);

        assertThatThrownBy(() -> axis.update(32767.1F, null, null))
                .isInstanceOf(AxisLimitExceededException.class);
        assertThatThrownBy(() -> axis.update(-1F, null, null))
                .isInstanceOf(AxisLimitExceededException.class);
    }
}