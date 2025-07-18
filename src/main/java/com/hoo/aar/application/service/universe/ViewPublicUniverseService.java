package com.hoo.aar.application.service.universe;

import com.hoo.aar.application.port.in.universe.ViewPublicUniverseResult;
import com.hoo.aar.application.port.in.universe.ViewPublicUniverseUseCase;
import com.hoo.aar.application.port.out.persistence.universe.CheckIsLikedUniversePort;
import com.hoo.aar.application.port.out.persistence.universe.ViewPublicUniversePort;
import com.hoo.aar.application.service.AarErrorCode;
import com.hoo.aar.application.service.AarException;
import com.hoo.admin.domain.universe.PublicStatus;
import com.hoo.admin.domain.universe.TraversalComponents;
import com.hoo.admin.domain.universe.TreeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ViewPublicUniverseService implements ViewPublicUniverseUseCase {

    private final ViewPublicUniversePort viewPublicUniversePort;
    private final CheckIsLikedUniversePort checkLikedUniversePort;

    @Override
    public ViewPublicUniverseResult read(Long universeId, Long userId) {
        TraversalComponents components = viewPublicUniversePort.viewPublicUniverse(universeId);

        checkPublicOrMine(userId, components);

        TreeInfo root = TreeInfo.create(components);
        boolean isLiked = userId != null && checkLikedUniversePort.checkIsLiked(root.getUniverseTreeComponent().getId(), userId);

        return ViewPublicUniverseResult.of(root, userId, isLiked);
    }

    private static void checkPublicOrMine(Long userId, TraversalComponents components) {
        if (!components.getUniverse().isMine(userId) &&
            components.getUniverse().getBasicInfo().getPublicStatus().equals(PublicStatus.PRIVATE)
        )
            throw new AarException(AarErrorCode.NOT_OWNED_PRIVATE_UNIVERSE_ACCESS);
    }
}
