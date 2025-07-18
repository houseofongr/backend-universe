package com.hoo.common.internal.api;

import com.hoo.common.internal.api.dto.FileInfo;

import java.util.List;
import java.util.UUID;

public interface GetFileInfoAPI {
    FileInfo getFileUrl(UUID fileID);

    List<FileInfo> getFileUrls(UUID fileID);
}
