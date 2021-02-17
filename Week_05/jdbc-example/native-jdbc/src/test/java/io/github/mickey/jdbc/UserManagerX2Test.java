package io.github.mickey.jdbc;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserManagerX2Test {

    private static Connection connection;
    private static UserManagerX2 userManagerX2;

    @BeforeClass
    public static void init() {
        connection = ConnectionManager.getConnection();
        userManagerX2 = new UserManagerX2();
        userManagerX2.setConnection(connection);
    }


    @org.junit.Test
    public void add() throws SQLException {
        final User user = new User();
        user.setName("tom");
        user.setAge(45);
        final boolean result = userManagerX2.add(user);
        Assert.assertTrue(result);
    }

    @Test
    public void batchAdd() throws Exception {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final User u = new User();
            u.setName("xx"+i);
            u.setAge(20+i);
            users.add(u);
        }
        userManagerX2.batchAdd(users);
    }

    @org.junit.Test
    public void update() throws SQLException {
        User u = new User();
        u.setId(5);
        u.setName("jessy");
        u.setAge(50);

        final boolean r = userManagerX2.update(u);
        Assert.assertTrue(r);
    }

    @org.junit.Test
    public void remove() throws SQLException {
        int id = 5;
        final boolean r = userManagerX2.remove(id);
        Assert.assertTrue(r);
    }

    @org.junit.Test
    public void get() throws SQLException {
        int id = 4;
        final User user = userManagerX2.get(id);
        System.out.println(user);
    }

    @org.junit.Test
    public void getAll() throws SQLException {
        final List<User> users = userManagerX2.getAll();
        users.forEach(System.out::println);
    }

    @AfterClass
    public static void destroy() throws SQLException {
        connection.close();
    }
}