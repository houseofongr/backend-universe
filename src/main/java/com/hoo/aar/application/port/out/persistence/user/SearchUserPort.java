package com.hoo.aar.application.port.out.persistence.user;

import com.hoo.aar.application.port.in.user.SearchMyInfoResult;

public interface SearchUserPort {
    SearchMyInfoResult queryMyInfo(Long userId);
    boolean existUserByNickname(String nickname);
}
