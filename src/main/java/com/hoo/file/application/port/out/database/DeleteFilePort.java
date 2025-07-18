package com.hoo.file.application.port.out.database;

import java.util.List;

public interface DeleteFilePort {
    void delete(Long id);
    void deleteAll(List<Long> ids);
}
