package com.hoo.universe.adapter.out.persistence.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.hoo.universe.domain.vo.Point;

import java.io.IOException;

public class PointSerializer extends StdSerializer<Point> {

    public PointSerializer() {
        super(Point.class);
    }

    @Override
    public void serialize(Point value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartArray();
        gen.writeNumber(value.x());
        gen.writeNumber(value.y());
        gen.writeEndArray();
    }
}