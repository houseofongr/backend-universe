package com.hoo.aar.adapter.out.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.Set;

import static com.hoo.aar.adapter.out.cache.RedisKeys.*;
import static org.assertj.core.api.Assertions.assertThat;

@RedisTest
class RedisAdapterTest {

    @Autowired
    RedisAdapter sut;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @BeforeEach
    void clear() {
        Set<String> keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
    }

    @Test
    @DisplayName("이메일 인증코드 저장 테스트")
    void testSaveAuthnEmailCode() {
        // given
        String email = "test@example.com";
        String code = "123456";
        Duration ttl = Duration.ofMinutes(5);

        // when
        sut.saveEmailAuthnCode(email, code, ttl);

        // then
        assertThat(redisTemplate.opsForValue().get(EMAIL_AUTHN_CODE_PREFIX.getKey() + email)).isEqualTo(code);
        assertThat(redisTemplate.getExpire(EMAIL_AUTHN_CODE_PREFIX.getKey() + email)).isEqualTo(300);
    }

    @Test
    @DisplayName("이메일 인증코드 불러오기 테스트")
    void testLoadAuthnEmailCode() {
        // given
        String email = "test@example.com";
        String code = "123456";
        Duration ttl = Duration.ofMinutes(5);
        sut.saveEmailAuthnCode(email, code, ttl);

        // when
        String result = sut.loadAuthnCodeByEmail(email);

        // then
        assertThat(result).isEqualTo(code);
    }

    @Test
    @DisplayName("이메일 인증완료 테스트")
    void testSaveAuthnStatus() {
        // given
        String email = "test@example.com";
        Duration ttl = Duration.ofHours(1);

        // when
        sut.saveAuthenticated(email, ttl);

        // then
        assertThat(redisTemplate.opsForValue().get(EMAIL_AUTHN_STATUS_PREFIX.getKey() + email)).isEqualTo("1");
        assertThat(redisTemplate.getExpire(EMAIL_AUTHN_STATUS_PREFIX.getKey() + email)).isEqualTo(3600);
    }

    @Test
    @DisplayName("이메일 인증여부 확인 테스트")
    void testLoadAuthnStatus() {
        // given
        String email = "test@example.com";
        Duration ttl = Duration.ofHours(1);

        // when 1 : not saved
        assertThat(sut.loadAuthenticated(email)).isFalse();

        // when 2 : saved
        sut.saveAuthenticated(email, ttl);

        // then
        assertThat(sut.loadAuthenticated(email)).isTrue();
    }
}