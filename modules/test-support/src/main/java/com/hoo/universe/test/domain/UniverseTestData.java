package com.hoo.universe.test.domain;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.enums.AccessLevel;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Sound;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.Universe.UniverseID;
import com.hoo.universe.domain.vo.*;

import java.util.ArrayList;
import java.util.List;

import static com.hoo.universe.test.domain.PieceTestData.defaultPiece;
import static com.hoo.universe.test.domain.ShapeTestData.createNonOverlappingShapes;
import static com.hoo.universe.test.domain.SoundTestData.defaultSound;
import static com.hoo.universe.test.domain.SpaceTestData.*;

public class UniverseTestData {

    public static UniverseBuilder defaultUniverseOnly() {
        return new UniverseBuilder()
                .withUniverseID(new UniverseID(UuidCreator.getTimeOrderedEpoch()))
                .withCategory(new Category(UuidCreator.getTimeOrderedEpoch(), "Public", "공공"))
                .withAuthor(new Owner(UuidCreator.getTimeOrderedEpoch(), "leaf"))
                .withUniverseMetadata(UniverseMetadata.create(UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch(), AccessLevel.PUBLIC, List.of("우주", "별", "지구")))
                .withCommonMetadata(CommonMetadata.create("우주", "유니버스는 우주입니다."));
    }

    public static List<Space> defaultSpaceNodes(Universe universe) {

        List<Outline> outlines = createNonOverlappingShapes(4);

        Space 스페이스1 = defaultSpace()
                .withUniverse(universe)
                .withSpaceID(new Space.SpaceID(UuidCreator.getTimeOrderedEpoch()))
                .withOutline(outlines.get(0))
                .withCommonMetadata(CommonMetadata.create("스페이스1", "유니버스의 첫번째 스페이스")).build();

        Space 스페이스2 = defaultSpace()
                .withUniverse(universe)
                .withSpaceID(new Space.SpaceID(UuidCreator.getTimeOrderedEpoch()))
                .withOutline(outlines.get(1))
                .withCommonMetadata(CommonMetadata.create("스페이스2", "유니버스의 두번째 스페이스")).build();

        Space 스페이스3 = defaultSpace()
                .withUniverse(universe)
                .withSpaceID(new Space.SpaceID(UuidCreator.getTimeOrderedEpoch()))
                .withParentSpaceID(스페이스1.getId())
                .withOutline(outlines.get(2))
                .withCommonMetadata(CommonMetadata.create("스페이스3", "스페이스1의 첫번째 스페이스")).build();

        Space 스페이스4 = defaultSpace()
                .withUniverse(universe)
                .withSpaceID(new Space.SpaceID(UuidCreator.getTimeOrderedEpoch()))
                .withParentSpaceID(스페이스2.getId())
                .withOutline(outlines.get(3))
                .withCommonMetadata(CommonMetadata.create("스페이스4", "스페이스2의 첫번째 스페이스")).build();

        Space 스페이스5 = defaultSpace()
                .withUniverse(universe)
                .withSpaceID(new Space.SpaceID(UuidCreator.getTimeOrderedEpoch()))
                .withParentSpaceID(스페이스2.getId())
                .withOutline(outlines.get(4))
                .withCommonMetadata(CommonMetadata.create("스페이스5", "스페이스2의 두번째 스페이스")).build();

        return new ArrayList<>(List.of(스페이스1, 스페이스2, 스페이스3, 스페이스4, 스페이스5));
    }

    public static List<Piece> defaultPieceNodes(Universe universe, List<Space> spaces) {

        List<Outline> outlines = createNonOverlappingShapes(4);

        Piece 피스1 = defaultPiece()
                .withUniverse(universe)
                .withPieceID(new Piece.PieceID(UuidCreator.getTimeOrderedEpoch()))
                .withOutline(outlines.get(5))
                .withCommonMetadata(CommonMetadata.create("피스1", "유니버스의 첫번째 피스")).build();

        Piece 피스2 = defaultPiece()
                .withUniverse(universe)
                .withPieceID(new Piece.PieceID(UuidCreator.getTimeOrderedEpoch()))
                .withParentSpaceID(spaces.get(0).getId())
                .withOutline(outlines.get(6))
                .withCommonMetadata(CommonMetadata.create("피스2", "스페이스1의 첫번째 피스")).build();

        Piece 피스3 = defaultPiece()
                .withUniverse(universe)
                .withPieceID(new Piece.PieceID(UuidCreator.getTimeOrderedEpoch()))
                .withParentSpaceID(spaces.get(2).getId())
                .withOutline(outlines.get(7))
                .withCommonMetadata(CommonMetadata.create("피스3", "스페이스3의 첫번째 피스")).build();

        Piece 피스4 = defaultPiece()
                .withUniverse(universe)
                .withPieceID(new Piece.PieceID(UuidCreator.getTimeOrderedEpoch()))
                .withParentSpaceID(spaces.get(3).getId())
                .withOutline(outlines.get(8))
                .withCommonMetadata(CommonMetadata.create("피스4", "스페이스4의 첫번째 피스")).build();

        Piece 피스5 = defaultPiece()
                .withUniverse(universe)
                .withPieceID(new Piece.PieceID(UuidCreator.getTimeOrderedEpoch()))
                .withParentSpaceID(spaces.get(3).getId())
                .withOutline(outlines.get(9))
                .withCommonMetadata(CommonMetadata.create("피스5", "스페이스4의 두번째 피스")).build();

        Piece 피스6 = defaultPiece()
                .withUniverse(universe)
                .withPieceID(new Piece.PieceID(UuidCreator.getTimeOrderedEpoch()))
                .withParentSpaceID(spaces.get(4).getId())
                .withOutline(outlines.get(10))
                .withCommonMetadata(CommonMetadata.create("피스6", "스페이스5의 첫번째 피스")).build();

        Piece 피스7 = defaultPiece()
                .withUniverse(universe)
                .withPieceID(new Piece.PieceID(UuidCreator.getTimeOrderedEpoch()))
                .withParentSpaceID(spaces.get(4).getId())
                .withOutline(outlines.get(11))
                .withCommonMetadata(CommonMetadata.create("피스7", "스페이스5의 두번째 피스")).build();

        return new ArrayList<>(List.of(피스1, 피스2, 피스3, 피스4, 피스5, 피스6, 피스7));
    }

    public static List<Sound> defaultSoundNodes(List<Piece> pieces) {
        List<Sound> sounds = new ArrayList<>();

        for (int i = 0; i < pieces.size(); i++) {
            Piece piece = pieces.get(i);

            Sound sound1 = defaultSound()
                    .withPiece(piece)
                    .withSoundID(new Sound.SoundID(UuidCreator.getTimeOrderedEpoch()))
                    .withSoundMetadata(SoundMetadata.create(UuidCreator.getTimeOrderedEpoch(), false))
                    .withCommonMetadata(CommonMetadata.create("사운드" + (i * 2 + 1), "피스" + (i + 1) + "의 첫번째 사운드"))
                    .build();

            sounds.add(sound1);

            if (i % 2 == 0) {
                Sound sound2 = defaultSound()
                        .withPiece(piece)
                        .withSoundID(new Sound.SoundID(UuidCreator.getTimeOrderedEpoch()))
                        .withSoundMetadata(SoundMetadata.create(UuidCreator.getTimeOrderedEpoch(), false))
                        .withCommonMetadata(CommonMetadata.create("사운드" + (i * 2 + 2), "피스" + (i + 1) + "의 두번째 사운드"))
                        .build();

                sounds.add(sound2);
            }
        }

        return sounds;
    }

    /**
     * 트리구조
     * <pre>
     * Universe
     * ├── Space1
     * │   ├── Piece2
     * │   └── Space3
     * │       └── Piece3
     * ├── Space2
     * │   ├── Space4
     * │   │   ├── Piece4
     * │   │   └── Piece5
     * │   └── Space5
     * │       ├── Piece6
     * │       └── Piece7
     * └── Piece1
     * </pre>
     */
    public static Universe defaultUniverse() {
        Universe universe = defaultUniverseOnly()
                .withUniverseID(new Universe.UniverseID(UuidCreator.getTimeOrderedEpoch()))
                .build();

        List<Space> spaces = defaultSpaceNodes(universe);
        List<Piece> pieces = defaultPieceNodes(universe, spaces);
        defaultSoundNodes(pieces);

        return universe;
    }

    public static Universe defaultUniverseExceptSounds() {
        Universe universe = defaultUniverseOnly()
                .withUniverseID(new Universe.UniverseID(UuidCreator.getTimeOrderedEpoch()))
                .build();

        List<Space> spaces = defaultSpaceNodes(universe);
        defaultPieceNodes(universe, spaces);

        return universe;
    }

    public static class UniverseBuilder {
        private UniverseID id;
        private Category category;
        private Owner owner;
        private UniverseMetadata universeMetadata;
        private CommonMetadata commonMetadata;

        public UniverseBuilder withUniverseID(UniverseID universeID) {
            this.id = universeID;
            return this;
        }

        public UniverseBuilder withCategory(Category category) {
            this.category = category;
            return this;
        }

        public UniverseBuilder withAuthor(Owner owner) {
            this.owner = owner;
            return this;
        }

        public UniverseBuilder withUniverseMetadata(UniverseMetadata universeMetadata) {
            this.universeMetadata = universeMetadata;
            return this;
        }

        public UniverseBuilder withCommonMetadata(CommonMetadata commonMetadata) {
            this.commonMetadata = commonMetadata;
            return this;
        }

        public Universe build() {
            return Universe.create(this.id, this.category, this.owner, this.universeMetadata, this.commonMetadata).newUniverse();
        }
    }
}
