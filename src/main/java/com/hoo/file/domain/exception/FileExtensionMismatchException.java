package com.hoo.file.domain.exception;

import com.hoo.file.domain.FileType;

public class FileExtensionMismatchException extends RuntimeException {
    public FileExtensionMismatchException(FileType fileType, String fileName) {
        super("File Type And Extension is mismatch. File Type : " + fileType + " | file Name : " + fileName);
    }
}
