package io.github.mickey.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mickey
 * @date 2/17/21 20:05
 */
public class UserManagerX1 {

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean add(User user) throws SQLException {
        assert connection != null;
        final Statement s = connection.createStatement();

        String sql = String.format("INSERT INTO `user` ( `name`, `age`)VALUES ('%s', %d)", user.getName(),
                user.getAge());
        System.out.println(sql);
        return s.executeUpdate(sql) >0;
    }


    public void batchAdd(List<User> users) throws SQLException {
        assert connection != null;
        final Statement statement = connection.createStatement();
        for (User user : users) {
            String sql = String.format("INSERT INTO `user` ( `name`, `age`)VALUES ('%s', %d)", user.getName(),
                    user.getAge());
            statement.addBatch(sql);
        }
        try {
            statement.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        }finally {
            connection.commit();
        }
    }


    public boolean update(User user) throws SQLException {
        assert connection != null;
        final Statement statement = connection.createStatement();
        final String sql = String.format("UPDATE user u SET u.`name`= '%s' , u.`age`=%d WHERE u.id = %d", user.getName(), user.getAge(), user.getId());
        System.out.println(sql);
        return statement.executeUpdate(sql) >0;
    }

    public boolean remove(int id) throws SQLException {
        assert connection != null;
        final Statement statement = connection.createStatement();
        final String sql = String.format("DELETE  from user  WHERE id = %d", id);
        return statement.executeUpdate(sql) > 0;
    }

    public User get(int id ) throws SQLException {
        assert connection != null;
        final Statement statement = connection.createStatement();
        final String sql = String.format("SELECT * FROM user WHERE id =%d", id);
        final ResultSet result = statement.executeQuery(sql);
        User user = new User();
        if (result.next()) {
            final String name = result.getString("name");
            final int dbId= result.getInt("id");
            final int age= result.getInt("age");
            user.setName(name);
            user.setId(dbId);
            user.setAge(age);
        }
        return user;
    }

    public List<User> getAll() throws SQLException {
        assert connection != null;
        final Statement statement = connection.createStatement();
        final String sql = "select * from user";
        final ResultSet result = statement.executeQuery(sql);
        List<User> users = new ArrayList<>();
        while (result.next()) {
            User user = new User();
            final String name = result.getString("name");
            final int dbId= result.getInt("id");
            final int age= result.getInt("age");
            user.setName(name);
            user.setId(dbId);
            user.setAge(age);
            users.add(user);
        }
        return users;
    }



}
