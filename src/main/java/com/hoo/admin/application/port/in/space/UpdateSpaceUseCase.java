package com.hoo.admin.application.port.in.space;

import org.springframework.web.multipart.MultipartFile;

public interface UpdateSpaceUseCase {
    UpdateSpaceResult.Detail updateDetail(Long spaceId, UpdateSpaceCommand.Detail command);

    UpdateSpaceResult.Position updatePosition(Long spaceId, UpdateSpaceCommand.Position command);

    UpdateSpaceResult.InnerImage updateInnerImage(Long spaceId, MultipartFile innerImage);
}
