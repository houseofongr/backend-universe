package com.hoo.file.domain;

import lombok.Getter;

@Getter
public class File {
    private final FileId fileId;
    private final FileStatus status;
    private final OwnerId ownerId;
    private final FileSize size;

    private File(FileId fileId, FileStatus status, OwnerId ownerId, FileSize size) {
        this.fileId = fileId;
        this.status = status;
        this.ownerId = ownerId;
        this.size = size;
    }

    public static File create(FileId fileId, FileStatus fileStatus, OwnerId ownerId, FileSize size) {

        return new File(fileId, fileStatus, ownerId, size);
    }

    @Override
    public String toString() {
        return "File [" +
               "id : " + fileId +
               ", status : " + status +
               ", ownerId : " + ownerId +
               ", size : " + size +
               ']';
    }
}
