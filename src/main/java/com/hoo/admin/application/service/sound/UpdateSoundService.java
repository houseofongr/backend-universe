package com.hoo.admin.application.service.sound;

import com.hoo.admin.application.port.in.sound.UpdateSoundCommand;
import com.hoo.admin.application.port.in.sound.UpdateSoundResult;
import com.hoo.admin.application.port.in.sound.UpdateSoundUseCase;
import com.hoo.admin.application.port.out.sound.FindSoundPort;
import com.hoo.admin.application.port.out.sound.UpdateSoundPort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.universe.piece.sound.Sound;
import com.hoo.file.application.port.in.DeleteFileUseCase;
import com.hoo.file.application.port.in.UploadFileResult;
import com.hoo.file.application.port.in.UploadPublicAudioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateSoundService implements UpdateSoundUseCase {

    private final FindSoundPort findSoundPort;
    private final UpdateSoundPort updateSoundPort;
    private final UploadPublicAudioUseCase uploadPublicAudioUseCase;
    private final DeleteFileUseCase deleteFileUseCase;

    @Override
    public UpdateSoundResult.Detail updateDetail(Long soundId, UpdateSoundCommand command) {
        Sound sound = findSoundPort.find(soundId);

        sound.getBasicInfo().update(command.title(), command.description());
        sound.getBasicInfo().updateHideStatus(command.hidden());

        updateSoundPort.update(sound);

        return new UpdateSoundResult.Detail(
                String.format("[#%d]번 사운드의 상세정보가 수정되었습니다.", sound.getId()),
                sound.getBasicInfo().getTitle(),
                sound.getBasicInfo().getDescription(),
                sound.getBasicInfo().getHidden()
        );
    }

    @Override
    public UpdateSoundResult.Audio updateAudio(Long soundId, MultipartFile audioFile) {
        if (audioFile == null) throw new AdminException(AdminErrorCode.SOUND_FILE_REQUIRED);
        if (audioFile.getSize() > 2 * 1024 * 1024) throw new AdminException(AdminErrorCode.EXCEEDED_FILE_SIZE);

        Sound sound = findSoundPort.find(soundId);
        Long deletedAudioId = sound.getFileInfo().getAudioId();

        UploadFileResult.FileInfo newAudioFileInfo = uploadPublicAudioUseCase.publicUpload(audioFile);
        sound.getFileInfo().updateAudio(newAudioFileInfo.id());

        updateSoundPort.update(sound);
        deleteFileUseCase.deleteFile(deletedAudioId);

        return new UpdateSoundResult.Audio(
                String.format("[#%d]번 사운드의 오디오가 수정되었습니다.", sound.getId()),
                deletedAudioId,
                newAudioFileInfo.id()
        );
    }
}
