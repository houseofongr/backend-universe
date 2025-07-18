package com.hoo.file.application.port.out.filesystem;

import com.hoo.file.domain.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface WriteFilePort {
    void write(File file, MultipartFile multipartFile) throws IOException;
}
