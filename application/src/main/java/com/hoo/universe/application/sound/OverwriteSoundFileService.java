package com.hoo.universe.application.sound;

import com.hoo.common.internal.api.dto.UploadFileRequest;
import com.hoo.common.internal.api.dto.UploadFileResponse;
import com.hoo.common.internal.message.FileDeleteEventPublisher;
import com.hoo.common.internal.api.FileUploadAPI;
import com.hoo.universe.api.dto.result.sound.OverwriteSoundFileResult;
import com.hoo.universe.api.in.sound.OverwriteSoundFileUseCase;
import com.hoo.universe.api.out.persistence.HandleSoundEventPort;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Piece.PieceID;
import com.hoo.universe.domain.Sound;
import com.hoo.universe.domain.Sound.SoundID;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.sound.SoundFileOverwriteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OverwriteSoundFileService implements OverwriteSoundFileUseCase {

    private final LoadUniversePort loadUniversePort;
    private final HandleSoundEventPort handleSoundEventPort;
    private final FileUploadAPI fileUploadAPI;
    private final FileDeleteEventPublisher fileDeleteEventPublisher;

    @Override
    public OverwriteSoundFileResult overwriteSoundAudio(UUID universeID, UUID pieceID, UUID soundID, UploadFileRequest audio) {

        Universe universe = loadUniversePort.loadUniverseWithAllEntity(universeID);
        Piece piece = universe.getPiece(new PieceID(pieceID));
        Sound sound = piece.getSound(new SoundID(soundID));
        UploadFileResponse response = fileUploadAPI.uploadFile(audio);

        SoundFileOverwriteEvent event = sound.overwriteFile(response.id());

        handleSoundEventPort.handleSoundFileOverwriteEvent(event);
        fileDeleteEventPublisher.publishDeleteFilesEvent(event.oldAudioID());

        return new OverwriteSoundFileResult(
                event.oldAudioID(),
                event.newAudioID()
        );
    }
}
