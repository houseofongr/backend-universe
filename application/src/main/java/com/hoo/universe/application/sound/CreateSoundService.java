package com.hoo.universe.application.sound;

import com.hoo.common.IssueIDPort;
import com.hoo.common.internal.api.dto.UploadFileResponse;
import com.hoo.common.internal.api.FileUploadAPI;
import com.hoo.universe.api.dto.command.sound.CreateSoundCommand;
import com.hoo.universe.api.dto.result.sound.CreateSoundResult;
import com.hoo.universe.api.in.sound.CreateSoundUseCase;
import com.hoo.universe.api.out.persistence.HandleSoundEventPort;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.application.exception.DomainErrorCode;
import com.hoo.universe.application.exception.UniverseDomainException;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Piece.PieceID;
import com.hoo.universe.domain.Sound;
import com.hoo.universe.domain.Sound.SoundID;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.sound.SoundCreateEvent;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.SoundMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateSoundService implements CreateSoundUseCase {

    private final IssueIDPort issueIDPort;
    private final LoadUniversePort loadUniversePort;
    private final HandleSoundEventPort handleSoundEventPort;
    private final FileUploadAPI fileUploadAPI;

    @Override
    public CreateSoundResult createNewSound(UUID universeID, UUID pieceID, CreateSoundCommand command) {

        Universe universe = loadUniversePort.loadUniverseExceptSounds(universeID);
        Piece piece = universe.getPiece(new PieceID(pieceID));
        SoundID newSoundID = new SoundID(issueIDPort.issueNewID());

        UploadFileResponse audio = fileUploadAPI.uploadFile(command.audio());

        SoundCreateEvent event = piece.createSoundInside(newSoundID,
                SoundMetadata.create(audio.id(), command.metadata().hidden()),
                CommonMetadata.create(command.metadata().title(), command.metadata().description())
        );

        if (event.maxSoundExceeded()) throw new UniverseDomainException(DomainErrorCode.MAX_SOUND_EXCEED);

        Sound newSound = event.newSound();
        handleSoundEventPort.handleSoundCreateEvent(event);

        return new CreateSoundResult(
                newSound.getId().uuid(),
                newSound.getSoundMetadata().getAudioID(),
                newSound.getCommonMetadata().getTitle(),
                newSound.getCommonMetadata().getDescription(),
                newSound.getSoundMetadata().isHidden(),
                newSound.getCommonMetadata().getCreatedTime().toEpochSecond()
        );
    }
}
