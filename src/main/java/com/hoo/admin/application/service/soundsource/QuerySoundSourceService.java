package com.hoo.admin.application.service.soundsource;

import com.hoo.admin.application.port.in.soundsource.*;
import com.hoo.admin.application.port.out.soundsource.FindSoundSourcePort;
import com.hoo.admin.application.port.out.soundsource.QuerySoundSourcePort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.item.soundsource.SoundSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuerySoundSourceService implements QuerySoundSourceUseCase, QuerySoundSourceListUseCase {

    private final FindSoundSourcePort findSoundSourcePort;
    private final QuerySoundSourcePort querySoundSourcePort;

    @Override
    public QuerySoundSourceResult querySoundSource(Long id) {

        SoundSource soundSource = findSoundSourcePort.loadSoundSource(id)
                .orElseThrow(() -> new AdminException(AdminErrorCode.SOUND_SOURCE_NOT_FOUND));

        return QuerySoundSourceResult.of(soundSource);
    }

    @Override
    public QuerySoundSourceListResult querySoundSourceList(QuerySoundSourceListCommand command) {
        return querySoundSourcePort.querySoundSourceList(command);
    }
}
