package com.hoo.admin.application.service.space;

import com.hoo.admin.application.port.in.space.CreateSpaceCommand;
import com.hoo.admin.application.port.in.space.CreateSpaceResult;
import com.hoo.admin.application.port.in.space.CreateSpaceUseCase;
import com.hoo.admin.application.port.out.space.CreateSpacePort;
import com.hoo.admin.application.port.out.space.SaveSpacePort;
import com.hoo.admin.domain.universe.space.Space;
import com.hoo.file.application.port.in.UploadFileResult;
import com.hoo.file.application.port.in.UploadPublicImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateSpaceService implements CreateSpaceUseCase {

    private final UploadPublicImageUseCase uploadPublicImageUseCase;
    private final CreateSpacePort createSpacePort;
    private final SaveSpacePort saveSpacePort;

    @Override
    public CreateSpaceResult create(CreateSpaceCommand command) {
        UploadFileResult.FileInfo uploadFileResult = uploadPublicImageUseCase.publicUpload(command.imageFile());
        Space space = createSpacePort.createSpace(command, uploadFileResult.id());

        return saveSpacePort.save(space);
    }
}
