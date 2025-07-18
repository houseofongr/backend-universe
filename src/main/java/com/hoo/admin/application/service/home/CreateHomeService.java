package com.hoo.admin.application.service.home;

import com.hoo.admin.application.port.in.home.CreateHomeCommand;
import com.hoo.admin.application.port.in.home.CreateHomeResult;
import com.hoo.admin.application.port.in.home.CreateHomeUseCase;
import com.hoo.admin.application.port.out.home.CreateHomePort;
import com.hoo.admin.application.port.out.home.SaveHomePort;
import com.hoo.admin.application.port.out.house.FindHousePort;
import com.hoo.admin.application.port.out.user.FindUserPort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.home.Home;
import com.hoo.admin.domain.house.House;
import com.hoo.admin.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateHomeService implements CreateHomeUseCase {

    private final FindHousePort findHousePort;
    private final FindUserPort findUserPort;
    private final CreateHomePort createHomePort;
    private final SaveHomePort saveHomePort;

    @Override
    @Transactional
    public CreateHomeResult create(CreateHomeCommand command) {

        House house = findHousePort.load(command.houseId())
                .orElseThrow(() -> new AdminException(AdminErrorCode.HOUSE_NOT_FOUND));

        User user = findUserPort.loadUser(command.userId())
                .orElseThrow(() -> new AdminException(AdminErrorCode.USER_NOT_FOUND));

        Home home = createHomePort.createHome(house, user);

        Long savedId = saveHomePort.save(home);

        return new CreateHomeResult(savedId, home.getHomeDetail().getName());
    }

}
