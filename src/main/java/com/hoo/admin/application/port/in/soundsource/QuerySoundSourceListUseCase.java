package com.hoo.admin.application.port.in.soundsource;

public interface QuerySoundSourceListUseCase {
    QuerySoundSourceListResult querySoundSourceList(QuerySoundSourceListCommand command);
}
