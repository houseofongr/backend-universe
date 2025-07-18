package com.hoo.common.internal.message;

import java.util.List;
import java.util.UUID;

public interface DeleteFileEventPublisher {
    void publishDeleteFilesEvent(List<UUID> fileIDs);
    void publishDeleteFilesEvent(UUID... fileIDs);
}
