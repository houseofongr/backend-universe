package com.hoo.admin.adapter.in.web.user;

import com.hoo.admin.application.port.in.home.QueryUserHomesResult;
import com.hoo.admin.application.port.in.home.QueryUserHomesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetUserHomesController {

    private final QueryUserHomesUseCase queryUserHomesUseCase;

    @GetMapping("/admin/users/{userId}/homes")
    public ResponseEntity<QueryUserHomesResult> getHomeList(@PathVariable Long userId) {
        return new ResponseEntity<>(queryUserHomesUseCase.queryUserHomes(userId), HttpStatus.OK);
    }
}
