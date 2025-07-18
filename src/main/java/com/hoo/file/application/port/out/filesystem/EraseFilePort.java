package com.hoo.file.application.port.out.filesystem;

import com.hoo.file.domain.File;

import java.io.IOException;

public interface EraseFilePort {
    void erase(File file) throws IOException;
}
