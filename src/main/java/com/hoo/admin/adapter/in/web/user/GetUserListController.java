package com.hoo.admin.adapter.in.web.user;

import com.hoo.admin.application.port.in.user.QueryUserInfoCommand;
import com.hoo.admin.application.port.in.user.QueryUserInfoResult;
import com.hoo.admin.application.port.in.user.QueryUserInfoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetUserListController {

    private final QueryUserInfoUseCase queryUserInfoUseCase;

    @GetMapping("/admin/users")
    public ResponseEntity<QueryUserInfoResult> getUsers(Pageable pageable) {
        return new ResponseEntity<>(queryUserInfoUseCase.query(new QueryUserInfoCommand(pageable)), HttpStatus.OK);
    }
}
