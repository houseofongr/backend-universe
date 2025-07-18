package com.hoo.universe.test.domain;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.Space.SpaceID;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.Point;
import com.hoo.universe.domain.vo.Outline;
import com.hoo.universe.domain.vo.SpaceMetadata;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverseOnly;

public class SpaceTestData {

    public static SpaceBuilder defaultSpace() {

        return new SpaceBuilder()
                .withUniverse(defaultUniverseOnly().build())
                .withSpaceID(defaultSpaceID())
                .withSpaceMetadata(defaultSpaceMetadata())
                .withCommonMetadata(defaultSpaceCommonMetadata())
                .withOutline(defaultOutline());
    }

    public static SpaceID defaultSpaceID() {
        return new SpaceID(UuidCreator.getTimeOrderedEpoch());
    }

    public static SpaceMetadata defaultSpaceMetadata() {
        return SpaceMetadata.create(UuidCreator.getTimeOrderedEpoch(), false);
    }

    public static Outline defaultOutline() {
        return Outline.getRectangleBy2Point(Point.of(0.3f, 0.2f), Point.of(0.5f, 0.6f));
    }

    public static CommonMetadata defaultSpaceCommonMetadata() {
        return CommonMetadata.create("공간", "스페이스는 공간입니다.");
    }

    public static class SpaceBuilder {

        private Universe universe;
        private SpaceID spaceID;
        private SpaceID parentSpaceID;
        private SpaceMetadata spaceMetadata;
        private CommonMetadata commonMetadata;
        private Outline outline;

        public SpaceBuilder withUniverse(Universe universe) {
            this.universe = universe;
            return this;
        }

        public SpaceBuilder withSpaceID(SpaceID spaceID) {
            this.spaceID = spaceID;
            return this;
        }

        public SpaceBuilder withParentSpaceID(SpaceID parentSpaceID) {
            this.parentSpaceID = parentSpaceID;
            return this;
        }

        public SpaceBuilder withSpaceMetadata(SpaceMetadata spaceMetadata) {
            this.spaceMetadata = spaceMetadata;
            return this;
        }

        public SpaceBuilder withCommonMetadata(CommonMetadata commonMetadata) {
            this.commonMetadata = commonMetadata;
            return this;
        }

        public SpaceBuilder withOutline(Outline outline) {
            this.outline = outline;
            return this;
        }


        public Space build() {
            if (this.universe == null) throw new UnsupportedOperationException("상위 유니버스가 존재하지 않습니다.");
            return universe.createSpaceInside(this.spaceID, this.parentSpaceID, this.spaceMetadata, this.commonMetadata, this.outline).newSpace();
        }
    }
}
