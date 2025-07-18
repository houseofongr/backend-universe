package com.hoo.universe.adapter.out.internal.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoo.common.internal.message.dto.DeleteFileMessage;
import com.hoo.universe.application.exception.AdapterErrorCode;
import com.hoo.universe.application.exception.UniverseAdapterException;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class InternalMessageMapper {

    private final ObjectMapper objectMapper;

    public String mapToFileDeleteMessage(List<UUID> fileIDs) {
        List<DeleteFileMessage> message = fileIDs.stream().map(id -> new DeleteFileMessage(id, ZonedDateTime.now().toEpochSecond())).toList();
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new UniverseAdapterException(AdapterErrorCode.MESSAGE_MAPPING_FAILED);
        }
    }
}
