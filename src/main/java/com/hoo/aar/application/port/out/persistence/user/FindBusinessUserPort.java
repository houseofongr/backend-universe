package com.hoo.aar.application.port.out.persistence.user;

public interface FindBusinessUserPort {
    BusinessUserInfo findBusinessUserInfo(String email);
}
