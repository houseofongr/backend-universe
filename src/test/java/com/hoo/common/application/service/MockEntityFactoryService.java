package com.hoo.common.application.service;

import com.hoo.admin.application.port.in.space.CreateSpaceCommand;
import com.hoo.admin.domain.home.Home;
import com.hoo.admin.domain.house.House;
import com.hoo.admin.domain.house.room.Room;
import com.hoo.admin.domain.item.Circle;
import com.hoo.admin.domain.item.Ellipse;
import com.hoo.admin.domain.item.Item;
import com.hoo.admin.domain.item.Rectangle;
import com.hoo.admin.domain.item.soundsource.SoundSource;
import com.hoo.admin.domain.universe.PublicStatus;
import com.hoo.admin.domain.universe.Universe;
import com.hoo.admin.domain.universe.UniverseCategory;
import com.hoo.admin.domain.universe.space.Space;
import com.hoo.admin.domain.user.DeletedUser;
import com.hoo.admin.domain.user.User;
import com.hoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.admin.domain.user.snsaccount.SnsDomain;
import com.hoo.common.adapter.MockIdAdapter;
import org.springframework.mock.web.MockMultipartFile;

import java.time.ZonedDateTime;
import java.util.List;

public class MockEntityFactoryService {

    private static final EntityFactoryService factory = new EntityFactoryService(new MockIdAdapter());

    public static SnsAccount getSnsAccount() {
        return factory.createSnsAccount(SnsDomain.KAKAO, "SNS_ID", "남상엽", "leaf", "test@example.com");
    }

    public static User getUser() {
        return factory.createUser(getSnsAccount(), true, true);
    }

    public static House getHouse() throws Exception {
        return factory.createHouse("cozy house", "leaf", "this is cozy house", 5000f, 5000f, 1L, 2L, List.of(getRoom(), getRoom2()));
    }

    public static House loadHouse() throws Exception {
        House house = getHouse();
        return House.load(house.getHouseId().getId(), house.getHouseDetail().getTitle(), house.getHouseDetail().getAuthor(), house.getHouseDetail().getDescription(), house.getArea().getWidth(), house.getArea().getHeight(), ZonedDateTime.now(), ZonedDateTime.now(), house.getBasicImageFile().getFileId().getId(), house.getBorderImageFile().getFileId().getId(), List.of(getRoom(), getRoom2()));
    }

    public static Room getRoom() throws Exception {
        return factory.createRoom("거실", 0f, 0f, 0f, 5000f, 1000f, 3L);
    }

    public static Room getRoom2() throws Exception {
        return factory.createRoom("주방", 0f, 1000f, 0f, 5000f, 1000f, 4L);
    }

    public static Home getHome() throws Exception {
        return factory.createHome(getHouse(), getUser());
    }

    public static Home loadHome() throws Exception {
        Home home = getHome();
        return Home.load(home.getHomeId().getId(), home.getHouseId().getId(), home.getOwnerId(), home.getHomeDetail().getName(), ZonedDateTime.now(), ZonedDateTime.now());
    }

    public static Item getRectangleItem() throws Exception {
        Home home = getHome();
        return factory.createItem(home.getHomeId().getId(), getRoom().getRoomId().getId(), home.getOwnerId(), "설이", new Rectangle(100f, 100f, 10f, 10f, 5f));
    }

    public static Item getCircleItem() throws Exception {
        Home home = getHome();
        return factory.createItem(home.getHomeId().getId(), getRoom().getRoomId().getId(), home.getOwnerId(), "강아지", new Circle(200f, 200f, 10.5f));
    }

    public static Item getEllipseItem() throws Exception {
        Home home = getHome();
        return factory.createItem(home.getHomeId().getId(), getRoom().getRoomId().getId(), home.getOwnerId(), "화분", new Ellipse(500f, 500f, 15f, 15f, 90f));
    }

    public static Item getRectangleItem(Long id) throws Exception {
        Home home = getHome();
        return Item.create(id, home.getHomeId().getId(), getRoom().getRoomId().getId(), home.getOwnerId(), "설이", new Rectangle(100f, 100f, 10f, 10f, 5f));
    }

    public static Item getCircleItem(Long id) throws Exception {
        Home home = getHome();
        return Item.create(id, home.getHomeId().getId(), getRoom().getRoomId().getId(), home.getOwnerId(), "강아지", new Circle(200f, 200f, 10.5f));
    }

    public static Item getEllipseItem(Long id) throws Exception {
        Home home = getHome();
        return Item.create(id, home.getHomeId().getId(), getRoom().getRoomId().getId(), home.getOwnerId(), "화분", new Ellipse(500f, 500f, 15f, 15f, 90f));
    }

    public static SoundSource getSoundSource() throws Exception {
        return factory.createSoundSource(getEllipseItem().getItemId().getId(), 1L, "골골송", "2025년 설이가 보내는 골골송", null);
    }

    public static Item loadRectangleItem() throws Exception {
        Item rectangleItem = getRectangleItem();
        return Item.load(rectangleItem.getItemId().getId(), rectangleItem.getHomeId().getId(), rectangleItem.getRoomId().getId(), rectangleItem.getUserId().getId(), rectangleItem.getItemDetail().getName(), rectangleItem.getShape(), List.of(getSoundSource()));
    }

    public static SoundSource loadSoundSource() throws Exception {
        SoundSource soundSource = getSoundSource();
        return SoundSource.load(soundSource.getSoundSourceId().getId(), getRectangleItem().getItemId().getId(), soundSource.getFile().getFileId().getId(), soundSource.getSoundSourceDetail().getName(), soundSource.getSoundSourceDetail().getDescription(), ZonedDateTime.now(), ZonedDateTime.now(), soundSource.getActive().isActive());
    }

    public static DeletedUser getDeletedUser() {
        return factory.createDeletedUser(getUser(), true, true);
    }

    public static Universe getUniverse() {
        return Universe.create(11L, 100L, 12L, "우주", "유니버스는 우주입니다.", new UniverseCategory(1L, "카테고리", "category"), PublicStatus.PUBLIC, List.of("우주", "행성", "지구", "별"), User.load(1L, "leaf"));
    }


    public static Space getParentSpace() {
        MockMultipartFile basicImage = new MockMultipartFile("image", "image.png", "image/png", "basic image".getBytes());
        CreateSpaceCommand command = new CreateSpaceCommand(1L, -1L, "공간", "", 1f, 0.9f, 0.8f, 0.7f, false, basicImage);
        return factory.createSpace(command, 10L);
    }

//    public static Space getChildSpace(Long parentId) {
//        MockMultipartFile basicImage = new MockMultipartFile("image", "image.png", "image/png", "basic image".getBytes());
//        Space parentSpace = Space.loadSingle(parentId,10L,1L,"공간",null,ZonedDateTime.now(),ZonedDateTime.now(), 1f,0.9f,0.8f,0.7f, 1);
//        CreateSpaceCommand command = new CreateSpaceCommand(1L, parentSpace.getId(), "자식",null,1f,0.9f,0.8f,0.7f, basicImage);
//        return factory.createSpace(command, parentSpace,11L);
//    }
}