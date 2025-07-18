package com.hoo.admin.domain.universe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SocialInfoTest {

    @Test
    @DisplayName("해시태그 유효성 검사")
    void testHashtagLowerCase() {
        // given
        List<String> hashtags = new ArrayList<>(List.of("lower_case", "UPPER_CASE", "COMPLEX_case", "!@#%$_", "한글가능", "띄어쓰기 안됨"));

        // when
        SocialInfo socialInfo = new SocialInfo(0, 0L, hashtags);

        // then
        assertThat(socialInfo.getHashtags()).containsExactly("lower_case", "upper_case", "complex_case", "한글가능");
    }

}