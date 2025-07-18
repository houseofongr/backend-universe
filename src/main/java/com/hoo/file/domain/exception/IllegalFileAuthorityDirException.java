package com.hoo.file.domain.exception;

public class IllegalFileAuthorityDirException extends RuntimeException {
    public IllegalFileAuthorityDirException(String ownerityDir) {
        super("Illegal File Authority dir : " + ownerityDir);
    }
}
