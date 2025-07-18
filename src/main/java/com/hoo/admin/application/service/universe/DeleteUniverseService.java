package com.hoo.admin.application.service.universe;

import com.hoo.admin.application.port.in.space.DeleteSpaceResult;
import com.hoo.admin.application.port.in.space.DeleteSpaceUseCase;
import com.hoo.admin.application.port.in.universe.DeleteUniverseResult;
import com.hoo.admin.application.port.in.universe.DeleteUniverseUseCase;
import com.hoo.admin.application.port.out.universe.DeleteUniversePort;
import com.hoo.admin.application.port.out.universe.FindUniversePort;
import com.hoo.admin.domain.universe.TreeInfo;
import com.hoo.admin.domain.universe.Universe;
import com.hoo.file.application.port.in.DeleteFileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteUniverseService implements DeleteUniverseUseCase {

    private final FindUniversePort findUniversePort;
    private final DeleteUniversePort deleteUniversePort;
    private final DeleteSpaceUseCase deleteSpaceUseCase;
    private final DeleteFileUseCase deleteFileUseCase;

    @Override
    public DeleteUniverseResult delete(Long id) {

        TreeInfo root = TreeInfo.create(findUniversePort.findTreeComponents(id));
        Universe universe = (Universe) root.getUniverseTreeComponent();

        DeleteSpaceResult deleteSpaceResult = deleteSpaceUseCase.deleteSubtree(-1L, root);

        deleteSpaceResult.deletedAudioFileIds().add(universe.getFileInfo().getThumbMusicId());
        deleteSpaceResult.deletedImageFileIds().add(universe.getFileInfo().getThumbnailId());
        deleteSpaceResult.deletedImageFileIds().add(universe.getFileInfo().getImageId());

        deleteFileUseCase.deleteFiles(List.of(universe.getFileInfo().getThumbMusicId(), universe.getFileInfo().getThumbnailId(), universe.getFileInfo().getImageId()));

        deleteUniversePort.delete(universe.getId());

        deleteSpaceResult.sort();

        return new DeleteUniverseResult(String.format("[#%d]번 유니버스가 삭제되었습니다.", id),
                id,
                deleteSpaceResult.deletedSpaceIds(),
                deleteSpaceResult.deletedPieceIds(),
                deleteSpaceResult.deletedSoundIds(),
                deleteSpaceResult.deletedImageFileIds(),
                deleteSpaceResult.deletedAudioFileIds()
        );
    }
}
