package com.hoo.universe.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoo.common.internal.api.dto.FileCommand;
import com.hoo.common.web.dto.PageRequest;
import com.hoo.common.internal.api.dto.UploadFileCommand;
import com.hoo.universe.api.dto.command.CreateUniverseCommand;
import com.hoo.universe.api.dto.command.piece.CreatePieceWithTwoPointCommand;
import com.hoo.universe.api.dto.command.sound.CreateSoundCommand;
import com.hoo.universe.api.dto.command.space.CreateSpaceWithTwoPointCommand;
import com.hoo.universe.application.exception.AdapterErrorCode;
import com.hoo.universe.application.exception.UniverseAdapterException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
public class RequestMapper {

    private final ObjectMapper objectMapper;

    public FileCommand mapToFileCommand(MultipartFile file) {

        if (file == null) return null;

        try {
            return new FileCommand(
                    file.getInputStream(),
                    file.getSize(),
                    file.getName(),
                    file.getContentType()
            );
        } catch (IOException e) {
            throw new UniverseAdapterException(AdapterErrorCode.GET_FILE_INPUT_STREAM_FAILED);
        }
    }

    public CreateUniverseCommand.Metadata mapToCreateUniverseCommandMetadata(String metadata) {

        try {
            return objectMapper.readValue(metadata, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new UniverseAdapterException(AdapterErrorCode.BAD_METADATA_FORMAT);
        }
    }

    public CreateSpaceWithTwoPointCommand.Metadata mapToCreateSpaceCommandMetadata(String metadata) {
        try {
            return objectMapper.readValue(metadata, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new UniverseAdapterException(AdapterErrorCode.BAD_METADATA_FORMAT);
        }
    }

    public CreatePieceWithTwoPointCommand.Metadata mapToCreatePieceCommandMetadata(String metadata) {
        try {
            return objectMapper.readValue(metadata, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new UniverseAdapterException(AdapterErrorCode.BAD_METADATA_FORMAT);
        }
    }

    public CreateSoundCommand.Metadata mapToCreateSoundCommandMetadata(String metadata) {
        try {
            return objectMapper.readValue(metadata, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new UniverseAdapterException(AdapterErrorCode.BAD_METADATA_FORMAT);
        }
    }

    public PageRequest mapToPageable(Pageable pageable, String searchType, String keyword, String sortType, Boolean isAsc) {
        return new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), searchType, keyword, sortType, isAsc);
    }

    public PageRequest mapToPageable(Pageable pageable, String sortType, Boolean isAsc) {
        return new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), null, null, sortType, isAsc);
    }
}
