package com.hoo.admin.application.service.user;

import com.hoo.admin.application.port.in.user.*;
import com.hoo.admin.application.port.out.user.SearchUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchUserService implements SearchUserUseCase, QueryUserInfoUseCase {

    private final SearchUserPort searchUserPort;

    @Override
    @Transactional(readOnly = true)
    public SearchUserResult query(SearchUserCommand command) {
        return searchUserPort.search(command);
    }

    @Override
    public QueryUserInfoResult query(QueryUserInfoCommand command) {
        return searchUserPort.query(command);
    }
}
