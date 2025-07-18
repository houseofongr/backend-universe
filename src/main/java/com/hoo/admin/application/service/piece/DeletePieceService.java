package com.hoo.admin.application.service.piece;

import com.hoo.admin.application.port.in.piece.DeletePieceResult;
import com.hoo.admin.application.port.in.piece.DeletePieceUseCase;
import com.hoo.admin.application.port.out.piece.DeletePiecePort;
import com.hoo.admin.application.port.out.piece.FindPiecePort;
import com.hoo.admin.application.port.out.sound.DeleteSoundPort;
import com.hoo.admin.domain.universe.piece.Piece;
import com.hoo.admin.domain.universe.piece.sound.Sound;
import com.hoo.file.application.port.in.DeleteFileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class DeletePieceService implements DeletePieceUseCase {

    private final FindPiecePort findPiecePort;
    private final DeletePiecePort deletePiecePort;
    private final DeleteSoundPort deleteSoundPort;
    private final DeleteFileUseCase deleteFileUseCase;

    @Override
    public DeletePieceResult delete(Long pieceId) {
        Piece piece = findPiecePort.findWithSounds(pieceId);
        deletePiecePort.delete(pieceId);

        List<Long> deletedSoundIds = piece.getSounds().stream().map(Sound::getId).sorted().toList();
        List<Long> deletedImageFileIds = piece.getFileInfo().getImageId() == null? List.of() : List.of(piece.getFileInfo().getImageId());
        List<Long> deletedAudioFileIds = piece.getSounds().stream().map(sound -> sound.getFileInfo().getAudioId()).sorted().toList();

        deleteSoundPort.deleteAll(deletedSoundIds);
        deleteFileUseCase.deleteFiles(Stream.concat(deletedImageFileIds.stream(), deletedAudioFileIds.stream()).toList());

        return new DeletePieceResult(String.format("[#%d]번 피스가 삭제되었습니다.", pieceId),
                pieceId,
                deletedSoundIds,
                deletedImageFileIds,
                deletedAudioFileIds);
    }
}
