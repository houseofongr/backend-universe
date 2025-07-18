package com.hoo.file.adapter.out.persistence;

import com.hoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.hoo.file.application.port.out.database.DeleteFilePort;
import com.hoo.file.application.port.out.database.FindFilePort;
import com.hoo.file.application.port.out.database.SaveImageFilePort;
import com.hoo.file.domain.File;
import com.hoo.file.domain.exception.FileExtensionMismatchException;
import com.hoo.file.domain.exception.FileSizeLimitExceedException;
import com.hoo.file.domain.exception.IllegalFileAuthorityDirException;
import com.hoo.file.domain.exception.IllegalFileTypeDirException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FilePersistenceAdapter implements SaveImageFilePort, FindFilePort, DeleteFilePort {

    private final FileJpaRepository fileJpaRepository;
    private final FileMapper fileMapper;

    @Override
    public Long save(File file) {

        FileJpaEntity newEntity = FileJpaEntity.create(file);
        fileJpaRepository.save(newEntity);

        return newEntity.getId();
    }

    @SneakyThrows({FileSizeLimitExceedException.class, FileExtensionMismatchException.class, IllegalFileTypeDirException.class, IllegalFileAuthorityDirException.class})
    @Override
    public Optional<File> load(Long fileId) {

        Optional<FileJpaEntity> optional = fileJpaRepository.findById(fileId);

        if (optional.isPresent())
            return Optional.of(fileMapper.mapToDomainEntity(optional.get()));
        else
            return Optional.empty();
    }

    @Override
    public List<File> loadAll(List<Long> ids) {
        return fileJpaRepository.findAllById(ids).stream().map(fileMapper::mapToDomainEntity).toList();
    }

    @Override
    public void delete(Long id) {
        fileJpaRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<Long> ids) {
        fileJpaRepository.deleteAllById(ids);
    }
}
