package com.hoo.admin.adapter.in.web.user;

import com.hoo.admin.application.port.in.user.SearchUserCommand;
import com.hoo.admin.application.port.in.user.SearchUserResult;
import com.hoo.admin.application.port.in.user.SearchUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetUserListControllerV2 {

    private final SearchUserUseCase useCase;

    @GetMapping("/admin/users/v2")
    public ResponseEntity<SearchUserResult> getUsers(
            Pageable pageable,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sortType,
            @RequestParam(required = false) Boolean isAsc) {

        return ResponseEntity.ok(useCase.query(new SearchUserCommand(pageable, searchType, keyword, sortType, isAsc)));
    }
}
