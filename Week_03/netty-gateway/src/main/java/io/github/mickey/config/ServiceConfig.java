package io.github.mickey.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceConfig {

    private static final Map<String, List<String>> SERVICES;

    static {
        SERVICES = new ConcurrentHashMap<>();
        List<String> backendURLs = new ArrayList<>();
        backendURLs.add("http://localhost:8001/");
        backendURLs.add("http://localhost:8002/");
        backendURLs.add("http://localhost:8003/");
        SERVICES.put("backend", backendURLs);

    }

    public static List<String>  get(String key) {
        assert key != null;
        return SERVICES.get(key);
    }



}
