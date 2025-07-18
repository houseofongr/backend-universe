package com.hoo.admin.application.port.in.house;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CreateHouseUseCase {
    CreateHouseResult create(CreateHouseMetadata metadata, Map<String, MultipartFile> fileMap);
}
