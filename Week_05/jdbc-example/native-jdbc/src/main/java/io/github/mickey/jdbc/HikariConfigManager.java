package io.github.mickey.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author mickey
 * @date 2/17/21 23:23
 */
public class HikariConfigManager {

    public static Connection getConnection() {
        final InputStream is = ConnectionManager.class.getClassLoader().getResourceAsStream("db.properties");
        final Properties p = new Properties();
        try {
            p.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(p.getProperty("db.url"));
        config.setUsername(p.getProperty("db.username"));
        config.setPassword(p.getProperty("db.password"));
        final HikariDataSource datasource = new HikariDataSource(config);
        try {
            return datasource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
