package io.github.mickey.config;

import java.util.HashMap;
import java.util.Map;

public class ServiceConfig {

    private static final Map<String, String> SERVICES;

    static {
        SERVICES = new HashMap<>();
        SERVICES.put("backend", "http://localhost:8080/");
    }

    public static String get(String key) {
        assert key != null;
        return SERVICES.get(key);
    }



}
