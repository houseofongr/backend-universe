package com.hoo.admin.application.port.out.user;

import com.hoo.admin.application.port.in.user.QueryUserInfoCommand;
import com.hoo.admin.application.port.in.user.QueryUserInfoResult;
import com.hoo.admin.application.port.in.user.SearchUserCommand;
import com.hoo.admin.application.port.in.user.SearchUserResult;

public interface SearchUserPort {
    QueryUserInfoResult query(QueryUserInfoCommand command);

    SearchUserResult search(SearchUserCommand command);
}
