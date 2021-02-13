package io.github.mickey.profile.example2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author mickey
 * @date 2/13/21 10:24
 */
public class App {

    public static void main(String[] args) {
        App app = new App();
        Properties p = app.loadProperties("config.properties");
        p.forEach((k, v) -> System.out.println("k:" + k + ",v:" + v));
    }

    private Properties loadProperties(String filename) {
        Properties p = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(filename)){
            p.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }
}
