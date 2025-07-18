package com.hoo.file.adapter.out.persistence;

import com.hoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.file.application.service.FileProperties;
import com.hoo.file.domain.*;
import com.hoo.file.domain.exception.FileExtensionMismatchException;
import com.hoo.file.domain.exception.FileSizeLimitExceedException;
import com.hoo.file.domain.exception.IllegalFileAuthorityDirException;
import com.hoo.file.domain.exception.IllegalFileTypeDirException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileMapper {

    private final FileProperties fileProperties;

    public File mapToDomainEntity(FileJpaEntity fileJpaEntity) throws FileSizeLimitExceedException, FileExtensionMismatchException, IllegalFileTypeDirException, IllegalFileAuthorityDirException {

        FileId fileId = FileId.load(fileJpaEntity.getAbsolutePath(), fileJpaEntity.getRealFileName(), fileJpaEntity.getFileSystemName());

        FileStatus fileStatus = fileJpaEntity.getIsDeleted() ? FileStatus.DELETED : FileStatus.CREATED;

        OwnerId ownerId = fileJpaEntity.getOwner() == null ? null : new OwnerId(fileJpaEntity.getOwner().getId());

        FileSize fileSize = new FileSize(fileJpaEntity.getFileSize(), fileProperties.getFileSizeLimit());

        return File.create(fileId, fileStatus, ownerId, fileSize);
    }
}
