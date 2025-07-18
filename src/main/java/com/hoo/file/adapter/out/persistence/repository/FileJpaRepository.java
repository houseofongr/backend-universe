package com.hoo.file.adapter.out.persistence.repository;

import com.hoo.file.adapter.out.persistence.entity.FileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileJpaRepository extends JpaRepository<FileJpaEntity, Long> {

    FileJpaEntity findByFileSystemNameAndAbsolutePath(String fileSystemName, String absolutePath);
}
