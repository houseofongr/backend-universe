package com.hoo.admin.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    @DisplayName("삭제된 유저정보 획득 테스트")
    void testGetDeleteUserInfo() {
        // given
        User user = User.load(1L, "남상엽", "leaf", "test@example.com", true, true, ZonedDateTime.now(), ZonedDateTime.now(), List.of());

        // when
        UserInfo userInfo = user.getDeletedUserInfo();

        // then
        assertThat(userInfo.getNickname()).isEqualTo("l**f");
        assertThat(userInfo.getRealName()).isEqualTo("남*엽");
        assertThat(userInfo.getEmail()).isEqualTo("t**t@example.com");
    }

    @Test
    @DisplayName("비즈니스 유저 생성 테스트")
    void testCreateBusinessUser() {
        // given
        BusinessUser businessUser = BusinessUser.create(1L, "leaf", "test@example.com", true, true);

        // when
        User user = User.createBusinessUser(businessUser);

        // then
        assertThat(user.getUserInfo().getId()).isNull();
        assertThat(user.getUserInfo().getRealName()).isNull();
        assertThat(user.getUserInfo().getEmail()).isEqualTo("test@example.com");
        assertThat(user.getUserInfo().getNickname()).isEqualTo("leaf");
        assertThat(user.getAgreement().getTermsOfUseAgreement()).isTrue();
        assertThat(user.getAgreement().getPersonalInformationAgreement()).isTrue();
        assertThat(user.getSnsAccounts()).isEmpty();
        assertThat(user.getBaseTime()).isNull();
    }

}