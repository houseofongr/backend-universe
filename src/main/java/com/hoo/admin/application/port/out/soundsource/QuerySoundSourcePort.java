package com.hoo.admin.application.port.out.soundsource;

import com.hoo.admin.application.port.in.soundsource.QuerySoundSourceListCommand;
import com.hoo.admin.application.port.in.soundsource.QuerySoundSourceListResult;

public interface QuerySoundSourcePort {
    QuerySoundSourceListResult querySoundSourceList(QuerySoundSourceListCommand command);
}
