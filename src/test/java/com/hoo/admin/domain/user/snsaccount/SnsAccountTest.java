package com.hoo.admin.domain.user.snsaccount;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SnsAccountTest {

    @Test
    @DisplayName("계정 등록여부 확인")
    void testIsRegistered() {
        // given

        // when
        SnsAccount notRegistered = SnsAccount.create(1L, SnsDomain.KAKAO, "sns_id", "leaf", "leaf", "test@example.com");
        SnsAccount notRegistered2 = SnsAccount.load(1L, SnsDomain.KAKAO, "sns_id", "leaf", "leaf", "test@example.com", null, null, null);
        SnsAccount registeredSnsAccount = SnsAccount.load(1L, SnsDomain.KAKAO, "sns_id", "leaf", "leaf", "test@example.com", null, null, 1L);

        // then
        assertThat(notRegistered.isRegistered()).isFalse();
        assertThat(notRegistered2.isRegistered()).isFalse();
        assertThat(registeredSnsAccount.isRegistered()).isTrue();
    }

}