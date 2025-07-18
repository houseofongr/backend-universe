package com.hoo.admin.application.service.space;

import com.hoo.admin.application.port.in.space.UpdateSpaceCommand;
import com.hoo.admin.application.port.in.space.UpdateSpaceResult;
import com.hoo.admin.application.port.in.space.UpdateSpaceUseCase;
import com.hoo.admin.application.port.out.space.FindSpacePort;
import com.hoo.admin.application.port.out.space.UpdateSpacePort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.universe.space.Space;
import com.hoo.file.application.port.in.DeleteFileUseCase;
import com.hoo.file.application.port.in.UploadFileResult;
import com.hoo.file.application.port.in.UploadPublicImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateSpaceService implements UpdateSpaceUseCase {

    private final FindSpacePort findSpacePort;
    private final UploadPublicImageUseCase uploadPublicImageUseCase;
    private final DeleteFileUseCase deleteFileUseCase;
    private final UpdateSpacePort updateSpacePort;

    @Override
    public UpdateSpaceResult.Detail updateDetail(Long spaceId, UpdateSpaceCommand.Detail command) {
        Space space = findSpacePort.find(spaceId);

        space.getBasicInfo().update(command.title(), command.description());
        space.getBasicInfo().updateHiddenStatus(command.hidden());

        updateSpacePort.update(space);

        return UpdateSpaceResult.Detail.of(space);
    }

    @Override
    public UpdateSpaceResult.Position updatePosition(Long spaceId, UpdateSpaceCommand.Position command) {
        Space space = findSpacePort.find(spaceId);

        space.getPosInfo().update(command.startX(), command.startY(), command.endX(), command.endY());

        updateSpacePort.update(space);

        return UpdateSpaceResult.Position.of(space);
    }

    @Override
    public UpdateSpaceResult.InnerImage updateInnerImage(Long spaceId, MultipartFile innerImage) {

        if (innerImage == null) throw new AdminException(AdminErrorCode.SPACE_FILE_REQUIRED);
        if (innerImage.getSize() >= 5 * 1024 * 1024) throw new AdminException(AdminErrorCode.EXCEEDED_FILE_SIZE);

        Space targetSpace = findSpacePort.find(spaceId);

        Long beforeInnerImageId = targetSpace.getFileInfo().getImageId();
        UploadFileResult.FileInfo uploadedInnerImage = uploadPublicImageUseCase.publicUpload(innerImage);

        targetSpace.getFileInfo().updateImage(uploadedInnerImage.id());

        updateSpacePort.update(targetSpace);
        deleteFileUseCase.deleteFile(beforeInnerImageId);

        return UpdateSpaceResult.InnerImage.of(targetSpace.getId(), beforeInnerImageId, uploadedInnerImage.id());
    }
}
