package io.github.mickey.cache.repository;

import com.zaxxer.hikari.HikariDataSource;
import io.github.mickey.cache.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author mickey
 * @date 2/18/21 00:33
 */
@Repository
public class UserRepository {

    @Autowired
    private HikariDataSource hikariDataSource;

    public User getUser(int id) {
        System.out.println("getUser 走数据库....args:"+ id);
        try (Connection connection = hikariDataSource.getConnection()) {
            final Statement s = connection.createStatement();
            final String sql = String.format("SELECT * FROM user WHERE id = %d", id);
            final ResultSet result = s.executeQuery(sql);
            User user = new User();
            if (result.next()) {
                final String name = result.getString("name");
                final int dbId = result.getInt("id");
                final int age = result.getInt("age");
                user.setName(name);
                user.setId(dbId);
                user.setAge(age);
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUsers() {
        System.out.println("getUsers 走数据库....");
        try (Connection connection = hikariDataSource.getConnection()) {
            final Statement s = connection.createStatement();
            final String sql = "SELECT * FROM user";
            final ResultSet result = s.executeQuery(sql);
            List<User> users = new ArrayList<>();
            while (result.next()) {
                User user = new User();
                final String name = result.getString("name");
                final int dbId = result.getInt("id");
                final int age = result.getInt("age");
                user.setName(name);
                user.setId(dbId);
                user.setAge(age);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
