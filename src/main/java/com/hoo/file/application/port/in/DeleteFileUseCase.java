package com.hoo.file.application.port.in;

import java.util.List;

public interface DeleteFileUseCase {
    void deleteFile(Long id);
    void deleteFiles(List<Long> ids);
}
