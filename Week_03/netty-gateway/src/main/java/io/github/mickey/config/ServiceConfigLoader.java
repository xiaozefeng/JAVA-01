package io.github.mickey.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public class ServiceConfigLoader {

    private static final Map<String, List<String>> SERVICES = new ConcurrentHashMap<>();

    private static final String filename = "application.properties";

    static {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        properties.forEach((k, v) -> {
            String[] split = v.toString().split(",");
            SERVICES.put(k.toString(), Arrays.stream(split).collect(Collectors.toList()));
        });

    }

    public static List<String> get(String key) {
        if (key == null) return new ArrayList<>();
        return SERVICES.get(key);
    }

}
