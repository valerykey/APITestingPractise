package com.valeryk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.valeryk.valueobjects.response.Booking;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class JSONReader {

    public static ConfigMap loadJSON(String Path) throws IOException {
        ConfigMap configuration = ConfigMap.initConfigMap();
        try {
            ObjectReader mapper = new ObjectMapper().readerFor(JSONModel.class);
            InputStream input = new FileInputStream("src/main/resources/booking.json");
            JSONModel booking = mapper.readValue(input);
            configuration.put("configuration", booking);
            input.close();
        } catch (IOException e) {
            throw e;
        }
        return configuration;
    }

}
