package com.hoo.file.application.port.out.database;

import com.hoo.file.domain.File;

public interface SaveImageFilePort {
    Long save(File file);
}
