package com.valeryk;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.entity.StringEntity;

import java.io.IOException;

public class ObjectMapperWrapper {

    public static <T> StringEntity serialize(Object bean) throws IOException {
        return new StringEntity(new ObjectMapper().writeValueAsString(bean));
    }

    public static <T> T deserialize(Class<T> classType, String jsonBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonBody,classType);
    }
}
