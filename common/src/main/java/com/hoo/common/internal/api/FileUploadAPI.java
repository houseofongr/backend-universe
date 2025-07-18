package com.hoo.common.internal.api;

import com.hoo.common.internal.api.dto.UploadFileRequest;
import com.hoo.common.internal.api.dto.UploadFileResponse;

public interface FileUploadAPI {
    UploadFileResponse uploadFile(UploadFileRequest request);
}
