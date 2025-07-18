package com.hoo.admin.application.service;

import com.hoo.common.application.service.ApplicationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminException extends ApplicationException {

    public AdminException(AdminErrorCode error) {
        super(error);
        log.error("Admin error has been constructed : {}", error.getMessage());
    }

    public AdminException(AdminErrorCode error, String message) {
        super(error, message);
    }
}
