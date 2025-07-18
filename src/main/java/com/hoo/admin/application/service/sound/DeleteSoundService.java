package com.hoo.admin.application.service.sound;

import com.hoo.admin.application.port.in.sound.DeleteSoundResult;
import com.hoo.admin.application.port.in.sound.DeleteSoundUseCase;
import com.hoo.admin.application.port.out.sound.DeleteSoundPort;
import com.hoo.admin.application.port.out.sound.FindSoundPort;
import com.hoo.admin.domain.universe.piece.sound.Sound;
import com.hoo.file.application.port.in.DeleteFileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteSoundService implements DeleteSoundUseCase {

    private final FindSoundPort findSoundPort;
    private final DeleteFileUseCase deleteFileUseCase;
    private final DeleteSoundPort deleteSoundPort;

    @Override
    public DeleteSoundResult delete(Long soundId) {
        Sound sound = findSoundPort.find(soundId);

        deleteFileUseCase.deleteFile(sound.getFileInfo().getAudioId());
        deleteSoundPort.delete(sound.getId());

        return new DeleteSoundResult(
                String.format("[#%d]번 사운드가 삭제되었습니다.", sound.getId()),
                soundId,
                sound.getFileInfo().getAudioId()
        );
    }
}
