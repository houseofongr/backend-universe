package com.hoo.universe.application;

import com.hoo.universe.api.in.dto.UpdateSoundMetadataCommand;
import com.hoo.universe.api.in.dto.UpdateSoundMetadataResult;
import com.hoo.universe.api.in.UpdateSoundMetadataUseCase;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.api.out.UpdateSoundStatusPort;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Sound;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.SoundMetadataUpdateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateSoundMetadataService implements UpdateSoundMetadataUseCase {

    private final LoadUniversePort loadUniversePort;
    private final UpdateSoundStatusPort updateSoundStatusPort;

    @Override
    public UpdateSoundMetadataResult updateSoundMetadata(UUID universeID, UUID pieceID, UUID soundID, UpdateSoundMetadataCommand command) {
        Universe universe = loadUniversePort.loadUniverseWithAllEntity(universeID);
        Piece piece = universe.getPiece(new Piece.PieceID(pieceID));
        Sound sound = piece.getSound(new Sound.SoundID(soundID));

        SoundMetadataUpdateEvent event = sound.updateMetadata(command.title(), command.description(), command.hidden());

        updateSoundStatusPort.updateSoundMetadata(event);

        return new UpdateSoundMetadataResult(
                sound.getCommonMetadata().getTitle(),
                sound.getCommonMetadata().getDescription(),
                sound.getSoundMetadata().isHidden(),
                sound.getCommonMetadata().getUpdatedTime().toEpochSecond()
        );
    }
}
