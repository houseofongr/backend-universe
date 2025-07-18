package com.hoo.aar.application.service.home;

import com.hoo.aar.application.port.in.home.*;
import com.hoo.aar.application.port.out.persistence.home.CheckOwnerPort;
import com.hoo.aar.application.port.out.persistence.home.QueryHomePort;
import com.hoo.aar.application.service.AarErrorCode;
import com.hoo.aar.application.service.AarException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("AARQueryHomeService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryHomeService implements QueryUserHomesUseCase, QueryHomeRoomsUseCase, QueryRoomItemsUseCase, QueryItemSoundSourceUseCase, QuerySoundSourceUseCase, QuerySoundSourcesPathUseCase {

    private final CheckOwnerPort checkOwnerPort;
    private final QueryHomePort queryHomePort;

    @Override
    public QueryUserHomesResult queryUserHomes(Long userId) {
        return queryHomePort.queryUserHomes(userId);
    }

    @Override
    public QueryHomeRoomsResult queryHomeRooms(Long userId, Long homeId) {
        if (!checkOwnerPort.checkHome(userId, homeId))
            throw new AarException(AarErrorCode.NOT_OWNED_HOME);

        return queryHomePort.queryHomeRooms(homeId);
    }

    @Override
    public QueryRoomItemsResult queryRoomItems(Long userId, Long homeId, Long roomId) {
        if (!checkOwnerPort.checkHome(userId, homeId))
            throw new AarException(AarErrorCode.NOT_OWNED_HOME);

        if (!checkOwnerPort.checkRoom(homeId, roomId))
            throw new AarException(AarErrorCode.NOT_OWNED_ROOM);

        return queryHomePort.queryRoomItems(homeId, roomId);
    }

    @Override
    public QueryItemSoundSourcesResult queryItemSoundSources(Long userId, Long itemId) {

        if (!checkOwnerPort.checkItem(userId, itemId))
            throw new AarException(AarErrorCode.NOT_OWNED_ITEM);

        return queryHomePort.queryItemSoundSources(itemId);
    }

    @Override
    public QuerySoundSourceResult querySoundSource(Long userId, Long soundSourceId) {

        if (!checkOwnerPort.checkSoundSource(userId, soundSourceId))
            throw new AarException(AarErrorCode.NOT_OWNED_SOUND_SOURCE);

        return queryHomePort.querySoundSource(soundSourceId);
    }

    @Override
    public QuerySoundSourcesPathResult querySoundSourcesPath(QuerySoundSourcesPathCommand command) {
        return queryHomePort.querySoundSourcesPath(command);
    }
}
