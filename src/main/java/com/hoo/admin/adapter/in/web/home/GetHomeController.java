package com.hoo.admin.adapter.in.web.home;

import com.hoo.admin.application.port.in.home.QueryHomeResult;
import com.hoo.admin.application.port.in.home.QueryHomeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetHomeController {

    private final QueryHomeUseCase queryHomeUseCase;

    @GetMapping("/admin/homes/{homeId}")
    public ResponseEntity<QueryHomeResult> getHome(@PathVariable Long homeId) {
        return new ResponseEntity<>(queryHomeUseCase.queryHome(homeId), HttpStatus.OK);
    }

}
