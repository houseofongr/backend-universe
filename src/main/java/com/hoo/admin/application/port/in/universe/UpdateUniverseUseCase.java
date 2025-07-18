package com.hoo.admin.application.port.in.universe;

import org.springframework.web.multipart.MultipartFile;

public interface UpdateUniverseUseCase {

    UpdateUniverseResult.Detail updateDetail(Long universeId, UpdateUniverseCommand command);

    UpdateUniverseResult.ThumbMusic updateThumbMusic(Long universeId, MultipartFile thumbMusic);

    UpdateUniverseResult.Thumbnail updateThumbnail(Long universeId, MultipartFile thumbnail);

    UpdateUniverseResult.InnerImage updateInnerImage(Long universeId, MultipartFile innerImage);
}
