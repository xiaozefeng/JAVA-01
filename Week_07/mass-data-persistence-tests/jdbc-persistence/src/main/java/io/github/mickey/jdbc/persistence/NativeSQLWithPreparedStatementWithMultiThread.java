package io.github.mickey.jdbc.persistence;

import io.github.mickey.jdbc.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class NativeSQLWithPreparedStatementWithMultiThread {

    private final Connection connection;

    public NativeSQLWithPreparedStatementWithMultiThread(Connection connection) {
        this.connection = connection;
    }


    public void insertBatch(List<User> users) throws SQLException {
        String insertSQL = "insert into t_user (nickname, mobile, status,avatar, password,created_time, updated_time) values (?,?,?,?,?,now(),now())";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        for (User user : users) {
            preparedStatement.setString(1, user.getNickname());
            preparedStatement.setString(2, user.getMobile());
            preparedStatement.setInt(3, user.getStatus());
            preparedStatement.setString(4, user.getAvatar());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
    }


}
