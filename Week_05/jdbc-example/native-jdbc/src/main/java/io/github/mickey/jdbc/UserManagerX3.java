package io.github.mickey.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mickey
 * @date 2/17/21 20:05
 */
public class UserManagerX3 {

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean add(User user) throws SQLException {
        assert connection != null;
        String sql = "INSERT INTO `user` ( `name`, `age`)VALUES (?, ?)";
        System.out.println(sql);
        final PreparedStatement s = connection.prepareStatement(sql);
        s.setString(1, user.getName());
        s.setInt(2, user.getAge());
        return s.executeUpdate(sql) >0;
    }


    public boolean batchAdd(List<User> users) throws SQLException {
        assert connection != null;
        String sql = "INSERT INTO `user` ( `name`, `age`)VALUES (?, ?)";
        final PreparedStatement s = connection.prepareStatement(sql);
        connection.setAutoCommit(false);

        for (User user : users) {
            s.setString(1, user.getName());
            s.setInt(2, user.getAge());
            s.addBatch();
        }
        try {
            s.executeBatch();
            return true;
        }catch (Exception e){
            connection.rollback();
        }finally {
            connection.commit();
        }
        return false;
    }


    public boolean update(User user) throws SQLException {
        assert connection != null;
        final String sql = "UPDATE user u SET u.`name`= ? , u.`age`=? WHERE u.id = ?";
        System.out.println(sql);
        final PreparedStatement s = connection.prepareStatement(sql);
        s.setString(1, user.getName());
        s.setInt(2, user.getAge());
        s.setInt(3, user.getId());
        return s.executeUpdate() >0;
    }

    public boolean remove(int id) throws SQLException {
        assert connection != null;
        final String sql = "DELETE  from user  WHERE id = ?";
        final PreparedStatement s = connection.prepareStatement(sql);
        s.setInt(1, id);
        return s.executeUpdate() > 0;
    }

    public User get(int id ) throws SQLException {
        assert connection != null;
        final String sql = String.format("SELECT * FROM user WHERE id =%d", id);
        final PreparedStatement s = connection.prepareStatement(sql);
        final ResultSet result = s.executeQuery(sql);
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
        final String sql = "select * from user";
        final PreparedStatement s = connection.prepareStatement(sql);
        final ResultSet result = s.executeQuery(sql);
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
