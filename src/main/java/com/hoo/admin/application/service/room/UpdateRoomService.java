package com.hoo.admin.application.service.room;


import com.hoo.admin.application.port.in.room.UpdateRoomInfoCommand;
import com.hoo.admin.application.port.in.room.UpdateRoomInfoUseCase;
import com.hoo.admin.application.port.out.room.FindRoomPort;
import com.hoo.admin.application.port.out.room.UpdateRoomPort;
import com.hoo.admin.domain.house.room.Room;
import com.hoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateRoomService implements UpdateRoomInfoUseCase {

    private final FindRoomPort findRoomPort;
    private final UpdateRoomPort updateRoomPort;

    @Override
    @Transactional
    public MessageDto update(UpdateRoomInfoCommand command) {

        List<Long> ids = command.roomInfos().stream().map(UpdateRoomInfoCommand.RoomInfo::roomId).toList();

        List<Room> rooms = findRoomPort.loadAll(ids);

        loop:
        for (UpdateRoomInfoCommand.RoomInfo roomInfo : command.roomInfos()) {
            for (Room room : rooms) {
                if (!room.getRoomId().getId().equals(roomInfo.roomId())) continue;
                room.updateDetail(roomInfo.newName());
                break loop;
            }
        }

        int updateCount = updateRoomPort.update(rooms);

        return new MessageDto(updateCount + "개 룸의 정보 수정이 완료되었습니다.");
    }
}
