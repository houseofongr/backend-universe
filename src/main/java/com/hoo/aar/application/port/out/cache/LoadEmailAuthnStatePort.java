package com.hoo.aar.application.port.out.cache;

public interface LoadEmailAuthnStatePort {
    boolean loadAuthenticated(String email);
}
