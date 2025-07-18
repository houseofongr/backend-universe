package com.hoo.aar.adapter.out.cache;

import com.hoo.aar.application.port.out.cache.LoadEmailAuthnCodePort;
import com.hoo.aar.application.port.out.cache.LoadEmailAuthnStatePort;
import com.hoo.aar.application.port.out.cache.SaveEmailAuthnCodePort;
import com.hoo.aar.application.port.out.cache.SaveEmailAuthnStatePort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;

import static com.hoo.aar.adapter.out.cache.RedisKeys.*;

@Component
public class RedisAdapter implements SaveEmailAuthnCodePort, LoadEmailAuthnCodePort, SaveEmailAuthnStatePort, LoadEmailAuthnStatePort {

    private final ValueOperations<String, String> valueOperations;

    public RedisAdapter(RedisTemplate<String, String> redisTemplate) {
        this.valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public String loadAuthnCodeByEmail(String email) {
        return valueOperations.get(EMAIL_AUTHN_CODE_PREFIX.getKey() + email);
    }

    @Override
    public void saveEmailAuthnCode(String email, String code, Duration ttl) {
        valueOperations.set(EMAIL_AUTHN_CODE_PREFIX.getKey() + email, code, ttl);
    }

    @Override
    public void saveAuthenticated(String email, Duration ttl) {
        valueOperations.set(EMAIL_AUTHN_STATUS_PREFIX.getKey() + email, "1", ttl);
    }

    @Override
    public boolean loadAuthenticated(String email) {
        return Objects.equals(valueOperations.get(EMAIL_AUTHN_STATUS_PREFIX.getKey() + email), "1");
    }
}
