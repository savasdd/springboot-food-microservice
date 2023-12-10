package com.food.model.geometry.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.io.IOException;

public class PointDeserializer <T extends Point> extends JsonDeserializer<T> {

    private final GeometryFactory factory = new GeometryFactory();

    public T deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        return (T) point(node);
    }
    private Point point(JsonNode node) {
        Point result = null;
        String srid;
        ArrayNode coordinates = (ArrayNode) node.get("coordinates");
        JsonNode crsNode= node.get("crs");
        if (coordinates != null) {
            result = point(coordinates);
        }
        if (crsNode!= null) {
            JsonNode propertiesNode= crsNode.get("properties");
            srid = propertiesNode.get("name").textValue().replace("EPSG:", "");
            if (result==null) result=factory.createPoint(new Coordinate(0,0,0));

            int intSrid=Integer.parseInt(srid);
            result.setSRID(intSrid);
        }
        return result;
    }

    private Point point(ArrayNode coordinates) {
        Coordinate coordinate = toCoordinate(coordinates);
        return factory.createPoint(coordinate);
    }

    private Coordinate toCoordinate(ArrayNode node) {
        double x = 0, y = 0, z = Double.NaN;
        if (node.size() > 1) {
            x = node.get(0).asDouble();
            y = node.get(1).asDouble();
        }
        if (node.size() > 2) {
            z = node.get(2).asDouble();
        }
        return new Coordinate(x, y, z);
    }
}
