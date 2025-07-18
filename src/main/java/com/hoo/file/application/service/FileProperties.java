package com.hoo.file.application.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FileProperties {

    private final String baseDir;
    private final Long fileSizeLimit;

    public FileProperties(@Value("${file.size-limit}") Long fileSizeLimit,
                          @Value("${file.base-dir}") String baseDir) {
        this.fileSizeLimit = fileSizeLimit;
        this.baseDir = baseDir;
    }
}
