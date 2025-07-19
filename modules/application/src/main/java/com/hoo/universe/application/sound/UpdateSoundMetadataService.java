package com.hoo.universe.application.sound;

import com.hoo.universe.api.in.web.dto.command.UpdateSoundMetadataCommand;
import com.hoo.universe.api.in.web.dto.result.UpdateSoundMetadataResult;
import com.hoo.universe.api.in.web.usecase.UpdateSoundMetadataUseCase;
import com.hoo.universe.api.out.persistence.HandleSoundEventPort;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Sound;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.sound.SoundMetadataUpdateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateSoundMetadataService implements UpdateSoundMetadataUseCase {

    private final LoadUniversePort loadUniversePort;
    private final HandleSoundEventPort handleSoundEventPort;

    @Override
    public UpdateSoundMetadataResult updateSoundMetadata(UUID universeID, UUID pieceID, UUID soundID, UpdateSoundMetadataCommand command) {

        Universe universe = loadUniversePort.loadUniverseWithAllEntity(universeID);
        Piece piece = universe.getPiece(new Piece.PieceID(pieceID));
        Sound sound = piece.getSound(new Sound.SoundID(soundID));

        SoundMetadataUpdateEvent event = sound.updateMetadata(command.title(), command.description(), command.hidden());

        handleSoundEventPort.handleSoundMetadataUpdateEvent(event);

        return new UpdateSoundMetadataResult(
                sound.getCommonMetadata().getTitle(),
                sound.getCommonMetadata().getDescription(),
                sound.getSoundMetadata().isHidden(),
                sound.getCommonMetadata().getUpdatedTime().toEpochSecond()
        );
    }
}
