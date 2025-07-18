package com.hoo.admin.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserInfoTest {

    @Test
    @DisplayName("마스킹 된 이름 생성 테스트")
    void testCreateMaskedName() {
        // given
        UserInfo oneWordName = new UserInfo(1L, "남", "leaf", "test@example.com");
        UserInfo twoWordName = new UserInfo(1L, "남상", "leaf", "test@example.com");
        UserInfo threeWordName = new UserInfo(1L, "남상엽", "leaf", "test@example.com");
        UserInfo fourWordName = new UserInfo(1L, "남상엽돌", "leaf", "test@example.com");
        UserInfo englishName = new UserInfo(1L, "Genie Josh", "leaf", "test@example.com");

        // when
        String maskedOneName = oneWordName.getMaskedRealName();
        String maskedTwoName = twoWordName.getMaskedRealName();
        String maskedThreeName = threeWordName.getMaskedRealName();
        String maskedFourName = fourWordName.getMaskedRealName();
        String maskedEnglishName = englishName.getMaskedRealName();

        // then
        assertThat(maskedOneName).isEqualTo("*");
        assertThat(maskedTwoName).isEqualTo("남*");
        assertThat(maskedThreeName).isEqualTo("남*엽");
        assertThat(maskedFourName).isEqualTo("남**돌");
        assertThat(maskedEnglishName).isEqualTo("G***e J**h");
    }

    @Test
    @DisplayName("마스킹 된 닉네임 생성 테스트")
    void testCreateMaskedNickname() {
        // given
        UserInfo oneWordName = new UserInfo(1L, "leaf", "남", "test@example.com");
        UserInfo twoWordName = new UserInfo(1L, "leaf", "남상", "test@example.com");
        UserInfo threeWordName = new UserInfo(1L, "leaf", "남상엽", "test@example.com");
        UserInfo fourWordName = new UserInfo(1L, "leaf", "남상엽돌", "test@example.com");
        UserInfo englishName = new UserInfo(1L, "leaf", "Genie Josh", "test@example.com");

        // when
        String maskedOneName = oneWordName.getMaskedNickname();
        String maskedTwoName = twoWordName.getMaskedNickname();
        String maskedThreeName = threeWordName.getMaskedNickname();
        String maskedFourName = fourWordName.getMaskedNickname();
        String maskedEnglishName = englishName.getMaskedNickname();

        // then
        assertThat(maskedOneName).isEqualTo("*");
        assertThat(maskedTwoName).isEqualTo("남*");
        assertThat(maskedThreeName).isEqualTo("남*엽");
        assertThat(maskedFourName).isEqualTo("남**돌");
        assertThat(maskedEnglishName).isEqualTo("G***e J**h");
    }

    @Test
    @DisplayName("마스킹 된 이메일 생성 테스트")
    void testCreateMaskedEmail() {
        // given
        UserInfo email1 = new UserInfo(1L, "남상엽", "leaf", "t@example.com");
        UserInfo email2 = new UserInfo(1L, "남상엽", "leaf", "te@example.com");
        UserInfo email3 = new UserInfo(1L, "남상엽", "leaf", "tes@example.com");
        UserInfo email4 = new UserInfo(1L, "남상엽", "leaf", "test@example.com");
        UserInfo email5 = new UserInfo(1L, "남상엽", "leaf", "test1@example.com");

        // when
        String masked1 = email1.getMaskedEmail();
        String masked2 = email2.getMaskedEmail();
        String masked3 = email3.getMaskedEmail();
        String masked4 = email4.getMaskedEmail();
        String masked5 = email5.getMaskedEmail();

        // then
        assertThat(masked1).isEqualTo("*@example.com");
        assertThat(masked2).isEqualTo("t*@example.com");
        assertThat(masked3).isEqualTo("t*s@example.com");
        assertThat(masked4).isEqualTo("t**t@example.com");
        assertThat(masked5).isEqualTo("t***1@example.com");
    }
}