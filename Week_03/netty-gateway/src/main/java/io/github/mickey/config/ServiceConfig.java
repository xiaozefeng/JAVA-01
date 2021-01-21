package io.github.mickey.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceConfig {

    private static final Map<String, List<String>> SERVICES;

    private static final  List<String> DEFAULT_ENDPOINTS;

    static {
        SERVICES = new ConcurrentHashMap<>();
        DEFAULT_ENDPOINTS = new ArrayList<>();
        DEFAULT_ENDPOINTS.add("http://localhost:8001/");
        DEFAULT_ENDPOINTS.add("http://localhost:8002/");
        DEFAULT_ENDPOINTS.add("http://localhost:8003/");

        List<String> NETTY_SERVERS = new ArrayList<>();
        NETTY_SERVERS.add("http://localhost:8080");
        SERVICES.put("backend", DEFAULT_ENDPOINTS);
        SERVICES.put("netty", NETTY_SERVERS);
    }

    public static List<String>  get(String key) {
        assert key != null;
        return SERVICES.getOrDefault(key, DEFAULT_ENDPOINTS);
    }



}
