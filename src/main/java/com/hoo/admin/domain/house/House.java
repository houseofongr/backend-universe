package com.hoo.admin.domain.house;

import com.hoo.admin.domain.Area;
import com.hoo.admin.domain.exception.RoomNameNotFoundException;
import com.hoo.admin.domain.file.File;
import com.hoo.admin.domain.file.FileId;
import com.hoo.admin.domain.file.FileType;
import com.hoo.admin.domain.house.room.Room;
import com.hoo.common.domain.BaseTime;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class House {

    private final HouseId houseId;
    private final HouseDetail houseDetail;
    private final Area area;
    private final BaseTime baseTime;
    private final List<Room> rooms;
    private final File basicImageFile;
    private final File borderImageFile;

    private House(HouseId houseId, HouseDetail houseDetail, Area area, BaseTime baseTime, List<Room> rooms, File basicImageFile, File borderImageFile) {
        this.houseId = houseId;
        this.houseDetail = houseDetail;
        this.area = area;
        this.baseTime = baseTime;
        this.rooms = rooms;
        this.basicImageFile = basicImageFile;
        this.borderImageFile = borderImageFile;
    }

    public static House create(Long id, String title, String author, String description, Float width, Float height, Long defaultImageFileId, Long borderImageFileId, List<Room> rooms) {

        File defaultImageFile = new File(new FileId(defaultImageFileId), FileType.IMAGE);
        File borderImageFile = new File(new FileId(borderImageFileId), FileType.IMAGE);

        return new House(new HouseId(id), new HouseDetail(title, author, description), new Area(width, height), null, rooms, defaultImageFile, borderImageFile);
    }

    public static House load(Long houseId, String title, String author, String description, Float width, Float height, ZonedDateTime createdTime, ZonedDateTime updatedTime, Long defaultImageFileId, Long borderImageFileId, List<Room> rooms) {

        Area area = new Area(width, height);
        File defaultImageFile = new File(new FileId(defaultImageFileId), FileType.IMAGE);
        File borderImageFile = new File(new FileId(borderImageFileId), FileType.IMAGE);

        return new House(new HouseId(houseId), new HouseDetail(title, author, description), area, new BaseTime(createdTime, updatedTime), rooms, defaultImageFile, borderImageFile);
    }

    public void updateDetail(String title, String author, String description) {
        houseDetail.update(title, author, description);
    }

    public void updateRoomInfo(String originalName, String newName) {

        for (Room room : rooms) {
            if (room.getRoomDetail().getName().equals(originalName)) {
                room.getRoomDetail().update(newName);
                return;
            }
        }

        throw new RoomNameNotFoundException(houseDetail.getTitle(), originalName);
    }

}
