package io.github.mickey.jdbc.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

    private ConnectionManager(){}

    private static final String path = System.getProperty("path", "db-local.properties");

    public static Connection getConnection() {
        final InputStream is = ConnectionManager.class.getClassLoader().getResourceAsStream(path);
        final Properties p = new Properties();
        try {
            p.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return DriverManager.getConnection(p.getProperty("db.url"),
                    p.getProperty("db.username"),
                    p.getProperty("db.password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
