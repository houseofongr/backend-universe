package com.hoo.admin.application.service.item;

import com.hoo.admin.application.port.in.item.*;
import com.hoo.admin.application.port.out.home.FindHomePort;
import com.hoo.admin.application.port.out.item.*;
import com.hoo.admin.application.port.out.room.FindRoomPort;
import com.hoo.admin.application.port.out.user.FindUserPort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.item.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateItemService implements CreateItemUseCase, CreateAndUpdateItemUseCase {

    private final FindUserPort findUserPort;
    private final FindHomePort findHomePort;
    private final FindRoomPort findRoomPort;
    private final FindItemPort findItemPort;
    private final UpdateItemPort updateItemPort;
    private final SaveItemPort saveItemPort;
    private final CreateItemPort createItemPort;
    private final MappingItemShapePort mappingItemPort;

    @Override
    @Transactional
    public CreateItemResult create(Long homeId, Long roomId, Long userId, CreateItemCommand command) {

        if (!findHomePort.exist(homeId)) throw new AdminException(AdminErrorCode.HOME_NOT_FOUND);
        if (!findRoomPort.exist(roomId)) throw new AdminException(AdminErrorCode.ROOM_NOT_FOUND);
        if (!findUserPort.exist(userId)) throw new AdminException(AdminErrorCode.USER_NOT_FOUND);

        List<Item> newItems = command.items().stream().map(itemData -> createItemPort.createItem(homeId, roomId, userId, itemData.name(), createShape(itemData))).toList();

        return new CreateItemResult(saveItemPort.save(homeId, roomId, newItems));
    }

    @Override
    @Transactional
    public CreateAndUpdateItemResult createAndUpdate(Long homeId, Long roomId, Long userId, CreateAndUpdateItemCommand command) {

        if (!findHomePort.exist(homeId)) throw new AdminException(AdminErrorCode.HOME_NOT_FOUND);
        if (!findRoomPort.exist(roomId)) throw new AdminException(AdminErrorCode.ROOM_NOT_FOUND);
        if (!findUserPort.exist(userId)) throw new AdminException(AdminErrorCode.USER_NOT_FOUND);

        List<Long> itemIds = new ArrayList<>();
        List<Item> existItems = findItemPort.loadAllItemsInHomeAndRoom(homeId, roomId);
        for (Item item : existItems) {
            for (ItemData itemData : command.updateItems()) {
                if (!item.getItemId().getId().equals(itemData.id())) continue;
                item.update(itemData.name(), mappingItemPort.mapToShape(itemData));
                updateItemPort.updateItem(item);
                itemIds.add(itemData.id());
            }
        }

        List<Item> newItems = command.createItems().stream().map(itemData -> createItemPort.createItem(homeId, roomId, userId, itemData.name(), createShape(itemData))).toList();
        itemIds.addAll(saveItemPort.save(homeId, roomId, newItems));

        return new CreateAndUpdateItemResult(itemIds);
    }

    private Shape createShape(ItemData itemData) {
        switch (itemData.itemType()) {
            case RECTANGLE -> {
                return new Rectangle(itemData.rectangleData().x(), itemData.rectangleData().y(), itemData.rectangleData().width(), itemData.rectangleData().height(), itemData.rectangleData().rotation());
            }
            case CIRCLE -> {
                return new Circle(itemData.circleData().x(), itemData.circleData().y(), itemData.circleData().radius());
            }
            case ELLIPSE -> {
                return new Ellipse(itemData.ellipseData().x(), itemData.ellipseData().y(), itemData.ellipseData().radiusX(), itemData.ellipseData().radiusY(), itemData.ellipseData().rotation());
            }
            default ->
                    throw new AdminException(AdminErrorCode.ILLEGAL_SHAPE_TYPE, "잘못된 아이템 형태 : " + itemData.itemType());
        }
    }
}
