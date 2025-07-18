package com.hoo.aar.adapter.in.web;

import com.hoo.aar.application.service.AarErrorCode;
import com.hoo.common.adapter.in.web.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AarErrorCodeController {

    @GetMapping("/aar/error-codes")
    ResponseEntity<?> getError() {
        Map<String, ErrorResponse> responseMap = new HashMap<>();

        for (AarErrorCode errorCode : AarErrorCode.values())
            responseMap.put(errorCode.name(), ErrorResponse.of(errorCode));

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
}
