package com.hoo.common.internal.message;

import java.util.List;
import java.util.UUID;

public interface FileDeleteEventPublisher {
    void publishDeleteFilesEvent(List<UUID> fileIDs);
    void publishDeleteFilesEvent(UUID... fileIDs);
}
