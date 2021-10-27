package com.valeryk.config;

import com.valeryk.valueobjects.response.Booking;

import java.util.HashMap;

public class ConfigMap extends HashMap<String, JSONModel> {

    private static ConfigMap instance;

    public static ConfigMap initConfigMap(){
        return new ConfigMap();
    }

    private ConfigMap() {
    }
}
