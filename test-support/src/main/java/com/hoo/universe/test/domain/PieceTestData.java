package com.hoo.universe.test.domain;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Piece.PieceID;
import com.hoo.universe.domain.Space.SpaceID;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.PieceMetadata;
import com.hoo.universe.domain.vo.Point;
import com.hoo.universe.domain.vo.Outline;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverseOnly;

public class PieceTestData {

    public static PieceBuilder defaultPiece() {

        return new PieceBuilder()
                .withUniverse(defaultUniverseOnly().build())
                .withPieceID(defaultPieceID())
                .withPieceMetadata(defaultPieceMetadata())
                .withCommonMetadata(defaultPieceCommonMetadata())
                .withOutline(defaultOutline());
    }

    public static PieceID defaultPieceID() {
        return new PieceID(UuidCreator.getTimeOrderedEpoch());
    }

    public static Outline defaultOutline() {
        return Outline.getRectangleBy2Point(Point.of(0.7f, 0.7f), Point.of(0.8f, 0.8f));
    }

    public static PieceMetadata defaultPieceMetadata() {
        return PieceMetadata.create(null, false);
    }

    public static CommonMetadata defaultPieceCommonMetadata() {
        return CommonMetadata.create("조각", "피스는 조각입니다.");
    }

    public static class PieceBuilder {

        private Universe universe;
        private PieceID pieceID;
        private SpaceID parentSpaceID;
        private PieceMetadata pieceMetadata;
        private CommonMetadata commonMetadata;
        private Outline outline;

        public PieceBuilder withUniverse(Universe universe) {
            this.universe = universe;
            return this;
        }

        public PieceBuilder withPieceID(PieceID pieceID) {
            this.pieceID = pieceID;
            return this;
        }

        public PieceBuilder withParentSpaceID(SpaceID parentSpaceID) {
            this.parentSpaceID = parentSpaceID;
            return this;
        }

        public PieceBuilder withPieceMetadata(PieceMetadata pieceMetadata) {
            this.pieceMetadata = pieceMetadata;
            return this;
        }

        public PieceBuilder withCommonMetadata(CommonMetadata commonMetadata) {
            this.commonMetadata = commonMetadata;
            return this;
        }

        public PieceBuilder withOutline(Outline outline) {
            this.outline = outline;
            return this;
        }

        public Piece build() {
            if (this.universe == null) throw new UnsupportedOperationException("상위 유니버스가 존재하지 않습니다.");
            return universe.createPieceInside(this.pieceID, this.parentSpaceID, this.pieceMetadata, this.commonMetadata, this.outline).newPiece();
        }
    }
}
