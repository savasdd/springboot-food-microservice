package com.food.model.geometry.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.locationtech.jts.geom.*;

import java.io.IOException;

public class GeometryDeserializer<T extends Geometry> extends JsonDeserializer<T> {

    private GeometryFactory factory = new GeometryFactory();

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext arg1) throws IOException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        return (T) geometry(node);
    }

    private Geometry geometry(JsonNode node) {
        Geometry result = null;
        String type = node.get("type").textValue();
        ArrayNode coordinates = (ArrayNode) node.get("coordinates");

        if ("Point".equals(type)) {
            result = point(coordinates);
        } else if ("MultiPoint".equals(type)) {
            result = multiPoint(coordinates);
        } else if ("LineString".equals(type)) {
            result = lineString(coordinates);
        } else if ("MultiLineString".equals(type)) {
            result = multiLineString(coordinates);
        } else if ("Polygon".equals(type)) {
            result = polygon(coordinates);
        } else if ("MultiPolygon".equals(type)) {
            result = multiPolygon(coordinates);
        } else if ("GeometryCollection".equals(type)) {
            result = geometryCollection((ArrayNode) node.get("geometries"));
        } else if ("FeatureCollection".equals(type)) {
            result = featureCollection((ArrayNode) node.get("features"));
        }

        //Crs
        assert result != null;
        JsonNode crsNode= node.get("crs");
        if (crsNode!= null) {
            JsonNode propertiesNode= crsNode.get("properties");
            String srid = propertiesNode.get("name").textValue().replace("EPSG:", "");
            int intSrid=Integer.parseInt(srid);
            result.setSRID(intSrid);
//            if (intSrid > 0) {
//                result.setSRID(intSrid);
//            } else {
//                result.setSRID(4326);
//            }
        } else {
            result.setSRID(4326);
        }

        return result;
    }

    private Geometry point(ArrayNode coordinates) {
        Coordinate coordinate = toCoordinate(coordinates);
        return factory.createPoint(coordinate);
    }

    private Geometry multiPoint(ArrayNode nodes) {
        Coordinate[] coordinates = toCoordinateArray(nodes);
        return factory.createMultiPoint(coordinates);
    }

    private LineString lineString(ArrayNode nodes) {
        Coordinate[] coordinates = toCoordinateArray(nodes);
        return factory.createLineString(coordinates);
    }

    private MultiLineString multiLineString(ArrayNode nodes) {
        LineString[] lineStrings = new LineString[nodes.size()];
        for (int i = 0; i < lineStrings.length; ++i) {
            lineStrings[i] = lineString((ArrayNode) nodes.get(i));
        }
        return factory.createMultiLineString(lineStrings);
    }

    private Polygon polygon(ArrayNode nodes) {
        LinearRing outerRing = toLinearRing((ArrayNode) nodes.get(0));
        LinearRing[] innerRings = new LinearRing[nodes.size() - 1];
        for (int i = 0; i < innerRings.length; ++i) {
            innerRings[i] = toLinearRing((ArrayNode) nodes.get(i + 1));
        }
        return factory.createPolygon(outerRing, innerRings);
    }

    private MultiPolygon multiPolygon(ArrayNode nodes) {
        Polygon[] polygons = new Polygon[nodes.size()];
        for (int i = 0; i < polygons.length; ++i) {
            polygons[i] = polygon((ArrayNode) nodes.get(i));
        }
        return factory.createMultiPolygon(polygons);
    }

    private GeometryCollection geometryCollection(ArrayNode nodes) {
        Geometry[] geometries = new Geometry[nodes.size()];
        int ii = 0;
        for (int i = 0; i < geometries.length; ++i) {
            if (!nodes.get(i).isNull()) {
                geometries[i] = geometry(nodes.get(i));
                ii++;
            }
        }

        Geometry[] geoms = new Geometry[ii];
        int it = 0;
        for (Geometry geometry : geometries) {
            if (geometry != null) {
                geoms[it] = geometry;
                it++;
            }
        }
        return factory.createGeometryCollection(geoms);
    }

    private GeometryCollection featureCollection(ArrayNode nodes) {
//        ArrayNode nodes = (ArrayNode)node.get("geometries");
        Geometry[] geometries = new Geometry[nodes.size()];
        for (int i = 0; i < geometries.length; ++i) {
            geometries[i] = geometry(nodes.get(i).get("geometry"));
        }
        return factory.createGeometryCollection(geometries);
    }

    private LinearRing toLinearRing(ArrayNode nodes) {
        Coordinate[] coordinates = toCoordinateArray(nodes);
        return factory.createLinearRing(coordinates);
    }

    private Coordinate[] toCoordinateArray(ArrayNode nodes) {
        Coordinate[] result = new Coordinate[nodes.size()];
        for (int i = 0; i < result.length; ++i) {
            result[i] = toCoordinate((ArrayNode) nodes.get(i));
        }
        return result;
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
