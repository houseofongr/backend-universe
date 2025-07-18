package com.hoo.common.internal.api;

import com.hoo.common.internal.api.dto.UploadFileCommand;
import com.hoo.common.internal.api.dto.UploadFileResult;

public interface UploadFileAPI {
    UploadFileResult uploadFile(UploadFileCommand request);
}
