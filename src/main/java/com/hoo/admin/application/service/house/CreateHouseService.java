package com.hoo.admin.application.service.house;

import com.hoo.admin.application.port.in.house.CreateHouseMetadata;
import com.hoo.admin.application.port.in.house.CreateHouseResult;
import com.hoo.admin.application.port.in.house.CreateHouseUseCase;
import com.hoo.admin.application.port.out.house.CreateHousePort;
import com.hoo.admin.application.port.out.house.CreateRoomPort;
import com.hoo.admin.application.port.out.house.SaveHousePort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.admin.domain.house.House;
import com.hoo.admin.domain.house.room.Room;
import com.hoo.file.application.port.in.UploadFileResult;
import com.hoo.file.application.port.in.UploadPrivateImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CreateHouseService implements CreateHouseUseCase {

    private final SaveHousePort saveHousePort;
    private final UploadPrivateImageUseCase uploadPrivateImageUseCase;
    private final CreateHousePort createHousePort;
    private final CreateRoomPort createRoomPort;

    @Override
    @Transactional
    public CreateHouseResult create(CreateHouseMetadata metadata, Map<String, MultipartFile> fileMap) throws AdminException {

        if (!verifyDuplicateHouseImageFiles(metadata, fileMap))
            throw new AdminException(AdminErrorCode.DUPLICATE_HOUSE_PROFILE_IMAGE_AND_BORDER_IMAGE);

        List<MultipartFile> uploadingFiles = getUploadingFiles(metadata, fileMap);

        try {
            UploadFileResult uploadFileResult = uploadPrivateImageUseCase.privateUpload(uploadingFiles);

            List<Room> rooms = new ArrayList<>();
            Long basicImageId = null, borderImageId = null;
            for (UploadFileResult.FileInfo fileInfo : uploadFileResult.fileInfos()) {
                if (fileInfo.realName().equals(fileMap.get(metadata.house().houseForm()).getOriginalFilename()))
                    basicImageId = fileInfo.id();

                else if (fileInfo.realName().equals(fileMap.get(metadata.house().borderForm()).getOriginalFilename()))
                    borderImageId = fileInfo.id();

                else
                    for (CreateHouseMetadata.RoomData room : metadata.rooms())
                        if (fileInfo.realName().equals(fileMap.get(room.form()).getOriginalFilename()))
                            rooms.add(createRoomPort.createRoom(room.name(), room.x(), room.y(), room.z(), room.width(), room.height(), fileInfo.id()));
            }

            House newHouse = createHousePort.createHouse(metadata.house().title(), metadata.house().author(), metadata.house().description(), metadata.house().width(), metadata.house().height(), basicImageId, borderImageId, rooms);
            Long savedId = saveHousePort.save(newHouse);

            return new CreateHouseResult(savedId);

        } catch (AxisLimitExceededException e) {
            throw new AdminException(AdminErrorCode.AXIS_PIXEL_LIMIT_EXCEED);

        } catch (AreaLimitExceededException e) {
            throw new AdminException(AdminErrorCode.AREA_SIZE_LIMIT_EXCEED);

        }
    }

    private boolean verifyDuplicateHouseImageFiles(CreateHouseMetadata metadata, Map<String, MultipartFile> fileMap) {
        return !Objects.equals(fileMap.get(metadata.house().houseForm()).getOriginalFilename(), fileMap.get(metadata.house().borderForm()).getOriginalFilename());
    }

    private static List<MultipartFile> getUploadingFiles(CreateHouseMetadata metadata, Map<String, MultipartFile> fileMap) {
        List<MultipartFile> uploadingFiles = new ArrayList<>(metadata.rooms().stream().map(room -> fileMap.get(room.form())).toList());
        uploadingFiles.add(fileMap.get(metadata.house().houseForm()));
        uploadingFiles.add(fileMap.get(metadata.house().borderForm()));
        return uploadingFiles;
    }
}
