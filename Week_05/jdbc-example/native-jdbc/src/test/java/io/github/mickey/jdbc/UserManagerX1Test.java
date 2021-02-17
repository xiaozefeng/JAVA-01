package io.github.mickey.jdbc;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class UserManagerX1Test {

    private static Connection connection;
    private static UserManagerX1 userManagerX1;

    @BeforeClass
    public static void init() {
        connection = ConnectionManager.getConnection();
        userManagerX1 = new UserManagerX1();
        userManagerX1.setConnection(connection);
    }


    @org.junit.Test
    public void add() throws SQLException {
        final User user = new User();
        user.setName("tom");
        user.setAge(45);
        final boolean result = userManagerX1.add(user);
        Assert.assertTrue(result);
    }

    @org.junit.Test
    public void update() throws SQLException {
        User u = new User();
        u.setId(5);
        u.setName("jessy");
        u.setAge(50);

        final boolean r = userManagerX1.update(u);
        Assert.assertTrue(r);
    }

    @org.junit.Test
    public void remove() throws SQLException {
        int id = 5;
        final boolean r = userManagerX1.remove(id);
        Assert.assertTrue(r);
    }

    @org.junit.Test
    public void get() throws SQLException {
        int id = 4;
        final User user = userManagerX1.get(id);
        System.out.println(user);
    }

    @org.junit.Test
    public void getAll() throws SQLException {
        final List<User> users = userManagerX1.getAll();
        System.out.println(users);
    }

    @AfterClass
    public static void destroy() throws SQLException {
        connection.close();
    }
}