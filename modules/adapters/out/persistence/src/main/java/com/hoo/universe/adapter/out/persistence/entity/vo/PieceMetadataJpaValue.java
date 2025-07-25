package com.hoo.universe.adapter.out.persistence.entity.vo;

import com.hoo.universe.domain.event.PieceMetadataUpdateEvent;
import com.hoo.universe.domain.vo.PieceMetadata;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.hoo.common.util.OptionalUtil.getOrDefault;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PieceMetadataJpaValue {

    @Column(columnDefinition = "BINARY(16)", name = "IMAGE_FILE_ID")
    private UUID imageFileID;

    @Column(nullable = false)
    private Boolean hidden;

    public static PieceMetadataJpaValue from(PieceMetadata pieceMetadata) {
        return new PieceMetadataJpaValue(
                pieceMetadata.getImageID(),
                pieceMetadata.isHidden()
        );
    }

    public void applyEvent(PieceMetadataUpdateEvent event) {
        this.hidden = getOrDefault(event.hidden(), hidden);
    }

}
