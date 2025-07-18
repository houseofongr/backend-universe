package com.hoo.admin.adapter.out.persistence;

import com.hoo.admin.adapter.out.persistence.mapper.ItemMapper;
import com.hoo.admin.adapter.out.persistence.mapper.SoundSourceMapper;
import com.hoo.admin.domain.item.Ellipse;
import com.hoo.admin.domain.item.Item;
import com.hoo.admin.domain.item.ItemType;
import com.hoo.admin.domain.item.Rectangle;
import com.hoo.common.adapter.out.persistence.PersistenceAdapterTest;
import com.hoo.common.adapter.out.persistence.entity.ItemJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.ItemShapeEllipseJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.ItemJpaRepository;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Import({ItemPersistenceAdapter.class, ItemMapper.class, SoundSourceMapper.class})
class ItemPersistenceAdapterTest {

    @Autowired
    ItemPersistenceAdapter sut;
    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Test
    @Sql("ItemPersistenceAdapterTest.sql")
    @DisplayName("아이템 저장 테스트")
    void testSaveItem() throws Exception {
        // given
        List<Item> items = List.of(MockEntityFactoryService.getCircleItem(), MockEntityFactoryService.getEllipseItem(), MockEntityFactoryService.getRectangleItem());

        // when
        List<Long> savedItemId = sut.save(1L, 1L, items);

        // then
        assertThat(savedItemId).hasSize(3);
    }

    @Test
    @Sql("ItemPersistenceAdapterTest2.sql")
    @DisplayName("아이템 조회 테스트")
    void testFindItem() {
        // given

        // when
        Optional<Item> item1 = sut.loadItem(1L);
        Optional<Item> item2 = sut.loadItem(2L);
        Optional<Item> item3 = sut.loadItem(3L);

        // then
        assertThat(item1).isNotEmpty();
        assertThat(item2).isNotEmpty();
        assertThat(item3).isNotEmpty();

        assertThat(item1.get().getItemDetail().getName()).isEqualTo("설이");
        assertThat(item2.get().getItemDetail().getName()).isEqualTo("강아지");
        assertThat(item3.get().getItemDetail().getName()).isEqualTo("화분");

        assertThat(item1.get().getSoundSources()).anySatisfy(soundSource ->
                assertThat(soundSource.getSoundSourceDetail().getName()).isEqualTo("골골송")
        );
    }

    @Test
    @Sql("ItemPersistenceAdapterTest2.sql")
    @DisplayName("홈에 있는 아이템 조회 테스트")
    void testFindItemInHome() {
        // given
        Long homeId = 5L;

        // when
        List<Item> items = sut.loadAllItemsInHome(homeId);

        // then
        assertThat(items).hasSize(3);
        assertThat(items).anySatisfy(item -> {
            assertThat(item.getShape().getItemType()).isEqualTo(ItemType.RECTANGLE);
            assertThat(item.getItemDetail().getName()).isEqualTo("설이");
            assertThat(item.getShape().getX()).isEqualTo(100);
            assertThat(item.getShape().getY()).isEqualTo(100);
            assertThat(((Rectangle) item.getShape()).getWidth()).isEqualTo(10);
            assertThat(((Rectangle) item.getShape()).getHeight()).isEqualTo(10);
            assertThat(((Rectangle) item.getShape()).getRotation()).isEqualTo(5);
        });
    }

    @Test
    @Sql("ItemPersistenceAdapterTest2.sql")
    @DisplayName("홈과 룸에 있는 아이템 조회 테스트")
    void testFindItemInHomeAndRoom() {
        // given
        Long homeId = 5L;
        Long roomId = 1L;

        // when
        List<Item> items = sut.loadAllItemsInHomeAndRoom(homeId, roomId);

        // then
        assertThat(items).hasSize(2);
        assertThat(items).anySatisfy(item -> {
            assertThat(item.getShape().getItemType()).isEqualTo(ItemType.RECTANGLE);
            assertThat(item.getItemDetail().getName()).isEqualTo("설이");
            assertThat(item.getShape().getX()).isEqualTo(100);
            assertThat(item.getShape().getY()).isEqualTo(100);
            assertThat(((Rectangle) item.getShape()).getWidth()).isEqualTo(10);
            assertThat(((Rectangle) item.getShape()).getHeight()).isEqualTo(10);
            assertThat(((Rectangle) item.getShape()).getRotation()).isEqualTo(5);
        });
    }

    @Test
    @Sql("ItemPersistenceAdapterTest2.sql")
    @DisplayName("룸 아이디로 아이템 존재여부 확인 테스트")
    void testExistItemByRoomId() {
        Long existId = 1L;
        Long notExistId = 3L;

        assertThat(sut.existItemByRoomId(existId)).isTrue();
        assertThat(sut.existItemByRoomId(notExistId)).isFalse();
    }

    @Test
    @Sql("ItemPersistenceAdapterTest2.sql")
    @DisplayName("아이템 수정 테스트")
    void testUpdateItem() {
        // given
        Item item = Item.create(1L, 1L, 1L, 10L, "고양이",
                new Ellipse(10f, 10f, 70.5f, 50f, 10f)
        );

        // when
        sut.updateItem(item);
        ItemJpaEntity itemJpaEntity = itemJpaRepository.findById(item.getItemId().getId()).get();

        // then
        assertThat(itemJpaEntity.getName()).isEqualTo("고양이");
        assertThat(itemJpaEntity.getShape()).isInstanceOf(ItemShapeEllipseJpaEntity.class);

        ItemShapeEllipseJpaEntity shape = (ItemShapeEllipseJpaEntity) itemJpaEntity.getShape();
        assertThat(shape.getX()).isEqualTo(10f);
        assertThat(shape.getY()).isEqualTo(10f);
        assertThat(shape.getRadiusX()).isEqualTo(70.5f);
        assertThat(shape.getRadiusY()).isEqualTo(50f);
        assertThat(shape.getRotation()).isEqualTo(10f);
    }

    @Test
    @Sql("ItemPersistenceAdapterTest2.sql")
    @DisplayName("아이템 삭제 테스트")
    void testDeleteItem() {
        // given
        Long id = 2L;

        // when
        sut.deleteItem(id);

        // then
        assertThat(itemJpaRepository.findById(id)).isEmpty();
    }
}