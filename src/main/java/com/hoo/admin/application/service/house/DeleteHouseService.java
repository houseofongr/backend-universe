package com.hoo.admin.application.service.house;

import com.hoo.admin.application.port.in.house.DeleteHouseUseCase;
import com.hoo.admin.application.port.in.room.DeleteRoomUseCase;
import com.hoo.admin.application.port.out.home.FindHomePort;
import com.hoo.admin.application.port.out.house.DeleteHousePort;
import com.hoo.admin.application.port.out.house.FindHousePort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.house.House;
import com.hoo.admin.domain.house.room.Room;
import com.hoo.common.application.port.in.MessageDto;
import com.hoo.file.application.port.in.DeleteFileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteHouseService implements DeleteHouseUseCase {

    private final FindHousePort findHousePort;
    private final FindHomePort findHomePort;
    private final DeleteHousePort deleteHousePort;
    private final DeleteRoomUseCase deleteRoomUseCase;
    private final DeleteFileUseCase deleteFileUseCase;

    @Override
    @Transactional
    public MessageDto deleteHouse(Long id) {

        House house = findHousePort.load(id)
                .orElseThrow(() -> new AdminException(AdminErrorCode.HOUSE_NOT_FOUND));

        if (findHomePort.existByHouseId(id))
            throw new AdminException(AdminErrorCode.HOLDING_HOME_HOUSE_DELETE);

        for (Room room : house.getRooms())
            deleteRoomUseCase.deleteRoom(room.getRoomId().getId());

        deleteFileUseCase.deleteFile(house.getBasicImageFile().getFileId().getId());
        deleteFileUseCase.deleteFile(house.getBorderImageFile().getFileId().getId());

        deleteHousePort.deleteHouse(house.getHouseId().getId());

        return new MessageDto(id + "번 하우스가 삭제되었습니다.");
    }

}
