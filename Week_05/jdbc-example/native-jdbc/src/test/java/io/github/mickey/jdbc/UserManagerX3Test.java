package io.github.mickey.jdbc;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserManagerX3Test {

    private static Connection connection;
    private static UserManagerX3 userManagerX3;

    @BeforeClass
    public static void init() {
        connection = HikariConfigManager.getConnection();
        userManagerX3 = new UserManagerX3();
        userManagerX3.setConnection(connection);
    }


    @Test
    public void add() throws SQLException {
        final User user = new User();
        user.setName("tom");
        user.setAge(45);
        final boolean result = userManagerX3.add(user);
        Assert.assertTrue(result);
    }

    @Test
    public void batchAdd() throws Exception {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            final User u = new User();
            u.setName("cc"+i);
            u.setAge(20+i);
            users.add(u);
        }
        userManagerX3.batchAdd(users);
    }

    @Test
    public void update() throws SQLException {
        User u = new User();
        u.setId(5);
        u.setName("jessy");
        u.setAge(50);

        final boolean r = userManagerX3.update(u);
        Assert.assertTrue(r);
    }

    @Test
    public void remove() throws SQLException {
        int id = 5;
        final boolean r = userManagerX3.remove(id);
        Assert.assertTrue(r);
    }

    @Test
    public void get() throws SQLException {
        int id = 4;
        final User user = userManagerX3.get(id);
        System.out.println(user);
    }

    @Test
    public void getAll() throws SQLException {
        final List<User> users = userManagerX3.getAll();
        users.forEach(System.out::println);
    }

    @AfterClass
    public static void destroy() throws SQLException {
        connection.close();
    }
}