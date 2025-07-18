package com.hoo.admin.application.service.house;

import com.hoo.admin.application.port.in.house.UpdateHouseInfoCommand;
import com.hoo.admin.application.port.in.house.UpdateHouseInfoUseCase;
import com.hoo.admin.application.port.out.house.FindHousePort;
import com.hoo.admin.application.port.out.house.UpdateHousePort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.house.House;
import com.hoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateHouseService implements UpdateHouseInfoUseCase {

    private final FindHousePort findHousePort;
    private final UpdateHousePort updateHousePort;

    @Override
    @Transactional
    public MessageDto update(UpdateHouseInfoCommand command) {

        House house = findHousePort.load(command.persistenceId())
                .orElseThrow(() -> new AdminException(AdminErrorCode.HOUSE_NOT_FOUND));

        house.updateDetail(command.title(), command.author(), command.description());
        updateHousePort.update(house);

        return new MessageDto(command.persistenceId() + "번 하우스 정보 수정이 완료되었습니다.");
    }

}
