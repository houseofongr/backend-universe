package com.hoo.aar.application.service.user;

import com.hoo.aar.application.port.in.user.SearchMyInfoResult;
import com.hoo.aar.application.port.in.user.QueryMyInfoUseCase;
import com.hoo.aar.application.port.out.persistence.user.SearchUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryMyInfoService implements QueryMyInfoUseCase {

    private final SearchUserPort searchUserPort;

    @Override
    public SearchMyInfoResult queryMyInfo(Long userId) {
        return searchUserPort.queryMyInfo(userId);
    }
}
