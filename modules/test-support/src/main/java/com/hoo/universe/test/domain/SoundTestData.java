package com.hoo.universe.test.domain;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Sound;
import com.hoo.universe.domain.Sound.SoundID;
import com.hoo.universe.domain.vo.*;

import static com.hoo.universe.test.domain.PieceTestData.defaultPiece;

public class SoundTestData {

    public static SoundBuilder defaultSound() {
        return new SoundBuilder()
                .withPiece(defaultPiece().build())
                .withSoundID(defaultSoundID())
                .withSoundMetadata(defaultSoundMetadata())
                .withCommonMetadata(defaultSoundCommonMetadata());
    }

    public static SoundID defaultSoundID() {
        return new SoundID(UuidCreator.getTimeOrderedEpoch());
    }

    public static SoundMetadata defaultSoundMetadata() {
        return SoundMetadata.create(UuidCreator.getTimeOrderedEpoch(), false);
    }

    public static CommonMetadata defaultSoundCommonMetadata() {
        return CommonMetadata.create("소리", "사운드는 소리입니다.");
    }

    public static class SoundBuilder {

        private Piece piece;
        private SoundID soundID;
        private SoundMetadata soundMetadata;
        private CommonMetadata commonMetadata;

        public SoundBuilder withPiece(Piece piece) {
            this.piece = piece;
            return this;
        }

        public SoundBuilder withSoundID(SoundID soundID) {
            this.soundID = soundID;
            return this;
        }

        public SoundBuilder withSoundMetadata(SoundMetadata soundMetadata) {
            this.soundMetadata = soundMetadata;
            return this;
        }

        public SoundBuilder withCommonMetadata(CommonMetadata commonMetadata) {
            this.commonMetadata = commonMetadata;
            return this;
        }

        public Sound build() {
            return piece.createSoundInside(soundID, soundMetadata, commonMetadata).newSound();
        }
    }
}
