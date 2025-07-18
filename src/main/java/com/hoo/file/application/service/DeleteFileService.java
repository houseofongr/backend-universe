package com.hoo.file.application.service;

import com.hoo.file.application.port.in.DeleteFileUseCase;
import com.hoo.file.application.port.out.database.DeleteFilePort;
import com.hoo.file.application.port.out.database.FindFilePort;
import com.hoo.file.application.port.out.filesystem.EraseFilePort;
import com.hoo.file.domain.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DeleteFileService implements DeleteFileUseCase {

    private final FindFilePort findFilePort;
    private final DeleteFilePort deleteFilePort;
    private final EraseFilePort eraseFilePort;

    @Override
    public void deleteFile(Long id) {
        try {

            File file = findFilePort.load(id)
                    .orElseThrow(() -> new FileException(FileErrorCode.FILE_NOT_FOUND));

            eraseFilePort.erase(file);

            deleteFilePort.delete(id);

            log.info("파일 삭제완료 : {}", file);

        } catch (IOException e) {
            throw new FileException(e, FileErrorCode.DELETE_FILE_FAILED);
        }
    }

    // TODO : 파일 부분삭제 시 삭제 예약 큐로 이동하는 로직 추가
    @Override
    public void deleteFiles(List<Long> ids) {
        try {
            List<File> files = findFilePort.loadAll(ids);

            for (File file : files) eraseFilePort.erase(file);

            deleteFilePort.deleteAll(ids);

            log.info("파일 전체 삭제완료 : {}", ids);

        } catch (IOException e) {
            throw new FileException(e, FileErrorCode.DELETE_FILE_FAILED);
        }
    }

}
