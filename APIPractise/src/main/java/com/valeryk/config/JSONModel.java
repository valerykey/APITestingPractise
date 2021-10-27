package com.valeryk.config;

import com.valeryk.valueobjects.response.Booking;

import java.util.Map;

public class JSONModel {
    Map<String, Booking> configuration;

    public void setConfiguration(Map<String, Booking> configuration) {
        this.configuration = configuration;
    }
}
