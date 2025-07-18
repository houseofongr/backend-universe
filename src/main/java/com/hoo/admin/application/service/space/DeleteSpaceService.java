package com.hoo.admin.application.service.space;

import com.hoo.admin.application.port.in.piece.DeletePieceResult;
import com.hoo.admin.application.port.in.piece.DeletePieceUseCase;
import com.hoo.admin.application.port.in.space.DeleteSpaceResult;
import com.hoo.admin.application.port.in.space.DeleteSpaceUseCase;
import com.hoo.admin.application.port.out.space.DeleteSpacePort;
import com.hoo.admin.application.port.out.space.FindSpacePort;
import com.hoo.admin.application.port.out.universe.FindUniversePort;
import com.hoo.admin.domain.universe.TraversalComponents;
import com.hoo.admin.domain.universe.TreeInfo;
import com.hoo.admin.domain.universe.space.Space;
import com.hoo.admin.domain.universe.piece.Piece;
import com.hoo.file.application.port.in.DeleteFileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteSpaceService implements DeleteSpaceUseCase {

    private final FindSpacePort findSpacePort;
    private final FindUniversePort findUniversePort;
    private final DeleteSpacePort deleteSpacePort;
    private final DeletePieceUseCase deletePieceUseCase;
    private final DeleteFileUseCase deleteFileUseCase;

    @Override
    public DeleteSpaceResult delete(Long spaceId) {
        Long universeId = findSpacePort.findUniverseId(spaceId);
        TreeInfo root = TreeInfo.create(findUniversePort.findTreeComponents(universeId));
        TreeInfo subtree = root.getComponent(Space.class, spaceId).getTreeInfo();

        DeleteSpaceResult result = deleteSubtree(spaceId, subtree);
        result.sort();

        return result;
    }

    @Override
    public DeleteSpaceResult deleteSubtree(Long spaceId, TreeInfo subtree) {
        TraversalComponents subComponents = subtree.getAllComponents();

        List<Long> deletedSpaceIds = new ArrayList<>();
        List<Long> deletedPieceIds = new ArrayList<>();
        List<Long> deletedSoundIds = new ArrayList<>();
        List<Long> deletedImageFileIds = new ArrayList<>();
        List<Long> deletedAudioFileIds = new ArrayList<>();

        for (Space space : subComponents.getSpaces()) {
            Long innerImageId = space.getFileInfo().getImageId();
            if (innerImageId != null && !innerImageId.equals(-1L)) deletedImageFileIds.add(innerImageId);
            deletedSpaceIds.add(space.getId());
        }

        for (Piece piece : subComponents.getPieces()) {
            Long innerImageId = piece.getFileInfo().getImageId();
            if (innerImageId != null && !innerImageId.equals(-1L)) deletedImageFileIds.add(innerImageId);
            deletedPieceIds.add(piece.getId());
        }

        for (Long deletePieceId : deletedPieceIds) {
            DeletePieceResult deletePieceResult = deletePieceUseCase.delete(deletePieceId);
            deletedSoundIds.addAll(deletePieceResult.deletedSoundIds());
            deletedAudioFileIds.addAll(deletePieceResult.deletedAudioFileIds());
        }

        deleteFileUseCase.deleteFiles(deletedImageFileIds);
        deleteSpacePort.deleteAll(deletedSpaceIds);

        return new DeleteSpaceResult(
                String.format("[#%d]번 스페이스가 삭제되었습니다.", spaceId),
                deletedSpaceIds,
                deletedPieceIds,
                deletedSoundIds,
                deletedImageFileIds,
                deletedAudioFileIds);
    }
}
