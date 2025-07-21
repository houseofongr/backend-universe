package com.hoo.universe.application;

import com.hoo.common.IssueIDPort;
import com.hoo.common.internal.api.dto.UploadFileCommand;
import com.hoo.common.internal.api.dto.UploadFileResult;
import com.hoo.common.internal.api.UploadFileAPI;
import com.hoo.universe.api.in.dto.CreateSoundCommand;
import com.hoo.universe.api.in.dto.CreateSoundResult;
import com.hoo.universe.api.in.CreateSoundUseCase;
import com.hoo.universe.api.out.HandleSoundEventPort;
import com.hoo.universe.api.out.LoadUniversePort;
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
    private final UploadFileAPI uploadFileAPI;

    @Override
    public CreateSoundResult createNewSound(UUID universeID, UUID pieceID, CreateSoundCommand command) {

        Universe universe = loadUniversePort.loadUniverseExceptSounds(universeID);
        Piece piece = universe.getPiece(new PieceID(pieceID));
        SoundID newSoundID = new SoundID(issueIDPort.issueNewID());

        UploadFileResult audio = uploadFileAPI.uploadFile(UploadFileCommand.from(command.audio(), universe.getOwner().getId(), universe.getUniverseMetadata().getAccessLevel()));

        SoundCreateEvent event = piece.createSoundInside(newSoundID,
                SoundMetadata.create(audio.id(), command.metadata().hidden()),
                CommonMetadata.create(command.metadata().title(), command.metadata().description())
        );

        if (event.maxSoundExceeded()) throw new UniverseDomainException(DomainErrorCode.MAX_SOUND_EXCEED);

        Sound newSound = event.newSound();
        handleSoundEventPort.handleSoundCreateEvent(event);

        return new CreateSoundResult(
                newSound.getId().uuid(),
                audio.fileUrl(),
                newSound.getCommonMetadata().getTitle(),
                newSound.getCommonMetadata().getDescription(),
                newSound.getSoundMetadata().isHidden(),
                newSound.getCommonMetadata().getCreatedTime().toEpochSecond()
        );
    }
}
