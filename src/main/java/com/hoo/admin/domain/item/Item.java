package com.hoo.admin.domain.item;

import com.hoo.admin.domain.home.HomeId;
import com.hoo.admin.domain.house.room.RoomId;
import com.hoo.admin.domain.item.soundsource.SoundSource;
import com.hoo.admin.domain.user.UserId;
import lombok.Getter;

import java.util.List;

@Getter
public class Item {
    private final ItemId itemId;
    private final HomeId homeId;
    private final RoomId roomId;
    private final UserId userId;
    private final ItemDetail itemDetail;
    private final List<SoundSource> soundSources;
    private Shape shape;

    private Item(ItemId itemId, HomeId homeId, RoomId roomId, UserId userId, ItemDetail itemDetail, Shape shape, List<SoundSource> soundSources) {
        this.itemId = itemId;
        this.homeId = homeId;
        this.roomId = roomId;
        this.userId = userId;
        this.itemDetail = itemDetail;
        this.shape = shape;
        this.soundSources = soundSources;
    }

    public static Item create(Long id, Long homeId, Long roomId, Long userId, String name, Shape shape) {
        return new Item(
                new ItemId(id),
                new HomeId(homeId),
                new RoomId(roomId),
                new UserId(userId),
                new ItemDetail(name),
                shape,
                List.of());
    }

    public static Item load(Long id, Long homeId, Long roomId, Long userId, String name, Shape shape, List<SoundSource> soundSources) {
        return new Item(
                new ItemId(id),
                new HomeId(homeId),
                new RoomId(roomId),
                new UserId(userId),
                new ItemDetail(name),
                shape,
                soundSources
        );
    }

    public void update(String name, Shape shape) {
        if (name != null) this.itemDetail.updateName(name);
        if (shape != null) this.shape = shape;
    }

    public boolean hasSoundSource() {
        return soundSources != null && !soundSources.isEmpty();
    }
}
