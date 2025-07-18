package com.hoo.file.adapter.out.persistence.entity;

import com.hoo.common.adapter.out.persistence.entity.DateColumnBaseEntity;
import com.hoo.common.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.file.domain.File;
import com.hoo.file.domain.FileStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FILE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String realFileName;

    @Column(nullable = false, length = 255)
    private String fileSystemName;

    @Column(nullable = false, length = 255)
    private String absolutePath;

    @Column(nullable = false, length = 255)
    private Boolean isDeleted;

    @Column(nullable = false)
    private Long fileSize;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_ID")
    private UserJpaEntity owner;

    public static FileJpaEntity create(File file) {
        return new FileJpaEntity(
                null,
                file.getFileId().getRealFileName(),
                file.getFileId().getFileSystemName(),
                file.getFileId().getDirectory(),
                file.getStatus() == FileStatus.DELETED,
                file.getSize().getFileByte(),
                null);
    }
}
