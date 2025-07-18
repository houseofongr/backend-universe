package com.hoo.file.domain.exception;

public class FileSizeLimitExceedException extends RuntimeException {
    public FileSizeLimitExceedException(long actual, long permitted) {
        super("File Size Limit Exceeded. Permitted : " + permitted + " | Actual : " + actual);
    }
}
