package com.hoo.file.adapter.in.web.publics;

import com.hoo.common.adapter.in.web.ErrorResponse;
import com.hoo.file.application.service.FileErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FileErrorCodeController {

    @GetMapping("/public/error-codes")
    ResponseEntity<?> getError() {
        Map<String, ErrorResponse> responseMap = new HashMap<>();

        for (FileErrorCode errorCode : FileErrorCode.values())
            responseMap.put(errorCode.name(), ErrorResponse.of(errorCode));

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
}
