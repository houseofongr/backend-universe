package com.hoo.universe.adapter.out.persistence.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hoo.universe.domain.vo.Point;

import java.io.IOException;

public class PointDeserializer extends StdDeserializer<Point> {

    public PointDeserializer() {
        super(Point.class);
    }

    @Override
    public Point deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        JsonNode node = p.getCodec().readTree(p);

        if (!node.isArray() || node.size() != 2) {
            throw new IllegalArgumentException("Point는 2개의 숫자 배열이어야 합니다.");
        }

        double x = node.get(0).asDouble();
        double y = node.get(1).asDouble();

        return Point.of(x, y);
    }
}
