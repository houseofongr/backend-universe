package com.hoo.admin.application.service.room;

import com.hoo.admin.application.port.in.room.DeleteRoomUseCase;
import com.hoo.admin.application.port.out.item.FindItemPort;
import com.hoo.admin.application.port.out.room.DeleteRoomPort;
import com.hoo.admin.application.port.out.room.FindRoomPort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.house.room.Room;
import com.hoo.common.application.port.in.MessageDto;
import com.hoo.file.application.port.in.DeleteFileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteRoomService implements DeleteRoomUseCase {

    private final FindRoomPort findRoomPort;
    private final FindItemPort findItemPort;
    private final DeleteRoomPort deleteRoomPort;
    private final DeleteFileUseCase deleteFileUseCase;

    @Override
    @Transactional
    public MessageDto deleteRoom(Long id) {
        Room room = findRoomPort.load(id)
                .orElseThrow(() -> new AdminException(AdminErrorCode.ROOM_NOT_FOUND));

        if (findItemPort.existItemByRoomId(id))
            throw new AdminException(AdminErrorCode.HOLDING_ITEM_ROOM_DELETE);

        deleteRoomPort.deleteRoom(id);
        deleteFileUseCase.deleteFile(room.getImageFile().getFileId().getId());

        return new MessageDto(id + "번 룸이 삭제되었습니다.");
    }

}
