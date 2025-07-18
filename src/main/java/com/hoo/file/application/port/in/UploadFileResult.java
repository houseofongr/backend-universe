package com.hoo.file.application.port.in;

import com.hoo.common.domain.Authority;
import com.hoo.file.domain.File;

import java.util.List;

public record UploadFileResult(
        List<FileInfo> fileInfos
) {
    public record FileInfo(
            Long id,
            Long ownerId,
            String realName,
            String fileSystemName,
            String size,
            Authority authority
    ) {
        public static FileInfo from(File file, Long id) {
            return new FileInfo(
                    id,
                    file.getOwnerId().getId(),
                    file.getFileId().getRealFileName(),
                    file.getFileId().getFileSystemName(),
                    file.getSize().getUnitSize(),
                    file.getFileId().getAuthority()
            );
        }
    }
}
