package com.hoo.universe.application;

import com.hoo.common.internal.api.dto.FileCommand;
import com.hoo.common.internal.api.dto.UploadFileCommand;
import com.hoo.common.internal.api.dto.UploadFileResult;
import com.hoo.common.internal.message.DeleteFileEventPublisher;
import com.hoo.common.internal.api.UploadFileAPI;
import com.hoo.universe.api.in.dto.OverwriteSoundFileResult;
import com.hoo.universe.api.in.OverwriteSoundFileUseCase;
import com.hoo.universe.api.out.HandleSoundEventPort;
import com.hoo.universe.api.out.LoadUniversePort;
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
    private final UploadFileAPI uploadFileAPI;
    private final DeleteFileEventPublisher deleteFileEventPublisher;

    @Override
    public OverwriteSoundFileResult overwriteSoundAudio(UUID universeID, UUID pieceID, UUID soundID, FileCommand audioCommand) {

        Universe universe = loadUniversePort.loadUniverseWithAllEntity(universeID);
        Piece piece = universe.getPiece(new PieceID(pieceID));
        Sound sound = piece.getSound(new SoundID(soundID));
        UploadFileResult audio = uploadFileAPI.uploadFile(UploadFileCommand.from(audioCommand, universe.getOwner().getId(), universe.getUniverseMetadata().getAccessLevel()));

        SoundFileOverwriteEvent event = sound.overwriteFile(audio.id());

        handleSoundEventPort.handleSoundFileOverwriteEvent(event);
        deleteFileEventPublisher.publishDeleteFilesEvent(event.oldAudioID());

        return new OverwriteSoundFileResult(audio.fileUrl());
    }
}
