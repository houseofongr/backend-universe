package com.hoo.universe.adapter.out.internal.message.file;

import com.hoo.common.internal.message.DeleteFileEventPublisher;
import com.hoo.universe.adapter.out.internal.message.InternalMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class DeleteFileEventKafkaAdapter implements DeleteFileEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final InternalMessageMapper internalMessageMapper;

    @Override
    public void publishDeleteFilesEvent(List<UUID> fileIDs) {
        String message = internalMessageMapper.mapToFileDeleteMessage(fileIDs);
        kafkaTemplate.send("file-delete", message);
    }

    @Override
    public void publishDeleteFilesEvent(UUID... fileIDs) {

    }
}
