package com.hoo.universe.adapter.out.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoo.universe.application.exception.AdapterErrorCode;
import com.hoo.universe.application.exception.UniverseAdapterException;
import com.hoo.universe.domain.vo.Point;
import com.hoo.universe.domain.vo.Outline;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OutlineSerializer {

    private final ObjectMapper objectMapper;

    public String serialize(Outline outline) {
        try {
            return objectMapper.writeValueAsString(outline.getPoints());
        } catch (JsonProcessingException e) {
            throw new UniverseAdapterException(AdapterErrorCode.POLYGON_SERIALIZE_FAILED);
        }
    }

    public Outline deserialize(String outlinePoints) {
        try {
            return Outline.of(objectMapper.<List<Point>>readValue(outlinePoints, new TypeReference<>() {
            }));
        } catch (JsonProcessingException e) {
            throw new UniverseAdapterException(AdapterErrorCode.POLYGON_DESERIALIZE_FAILED);
        }
    }

}
