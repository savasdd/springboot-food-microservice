package com.food.model.geometry.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;

import java.io.IOException;

public class PointSerializer extends JsonSerializer<Point> {

    public void serialize(Point point, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        write (point, gen);
    }

    private void write(Point point, JsonGenerator gen) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("type", "Point");
        gen.writeFieldName("coordinates");
        writeCoordinate(point.getCoordinate(), gen);
        gen.writeFieldName("crs");
        writeCrs(point, gen);
        gen.writeEndObject();
    }

    private void writeCoordinate(Coordinate coordinate, JsonGenerator gen) throws IOException {
        gen.writeStartArray();
        gen.writeNumber(coordinate.x);
        gen.writeNumber(coordinate.y);
        if (!Double.isNaN(coordinate.z)) {
            gen.writeNumber(coordinate.z);
        }
        gen.writeEndArray();
    }

    private void writeCrs(Point point, JsonGenerator gen) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("type", "name");
        gen.writeFieldName("properties");
        writeProperties(point.getSRID(), gen);
        gen.writeEndObject();
    }

    private void writeProperties(int srid, JsonGenerator gen) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("name", "EPSG:" + srid);
        gen.writeEndObject();
    }
}
