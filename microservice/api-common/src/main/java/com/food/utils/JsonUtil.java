package com.food.utils;

/**
 * @author murat
 */

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonUtil {
    public static Gson gson = new Gson();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonElement toJsonTree(Object src) {
        return gson.toJsonTree(src);
    }

    public static String toJson(Object src) {
        try {
            return objectMapper.writeValueAsString(src);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T fromJson(String src, Class<T> type) throws JsonSyntaxException {
        try {
            return objectMapper.readValue(src, type);
        } catch (Exception e) {
            try {
                return gson.fromJson(src, type);
            } catch (Exception ex) {
                return null;
            }
        }
    }

    public static <T> T fromJson(JsonElement src, Class<T> type) throws JsonSyntaxException {
        return gson.fromJson(src, type);
    }

    public static <T> List<T> fromJsonList(Object src) throws JsonSyntaxException {
        try {
            return (ArrayList<T>) src;
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> List<T> fromJsonList(Object src, Class<T> type) throws JsonSyntaxException {
        try {
            List<Object> data = objectMapper.readValue(toJson(src), new TypeReference<>() {
            });
            return data.stream().map(o -> objectMapper.convertValue(o, type)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
