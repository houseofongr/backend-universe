package com.hoo.aar.adapter.in.web.authn.security.jwt;

import com.hoo.aar.adapter.in.web.authn.security.JwtProperties;
import com.hoo.aar.adapter.out.jwt.JwtAdapter;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;

import java.security.SecureRandom;

public class MockJwtUtil {

    public static JwtAdapter getJwtUtil() {
        byte[] secretKey = new byte[32];
        new SecureRandom().nextBytes(secretKey);

        try {
            return new JwtAdapter(new MACSigner(secretKey),
                    new JwtProperties(
                            new String(secretKey),
                            "mock_jwt_util",
                            10000L
                    ));
        } catch (KeyLengthException e) {
            throw new RuntimeException(e);
        }
    }
}
