package io.github.mickey.jdbc.persistence;

import io.github.mickey.jdbc.domain.User;
import io.github.mickey.jdbc.util.PageUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class NativeSQLWithPreparedStatement implements UserRepository {

    private  final Connection connection;

    public NativeSQLWithPreparedStatement(Connection connection) {
        this.connection = connection;
    }



    @Override
    public void batchInsert(List<User> users) throws SQLException {
        String insertSQL = "insert into t_user (nickname, mobile, status,avatar, password,created_time, updated_time) values (?,?,?,?,?,now(),now())";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        int count = 20000;
        int times = calTimes(count, users.size());
        for (int i = 1; i <= times; i++) {
            List<User> currentBatch = getBatchUsers(users, i, count);
            for (User user : currentBatch) {
                preparedStatement.setString(1, user.getNickname());
                preparedStatement.setString(2, user.getMobile());
                preparedStatement.setInt(3, user.getStatus());
                preparedStatement.setString(4, user.getAvatar());
                preparedStatement.setString(5, user.getPassword());
                preparedStatement.addBatch();
            }
            preparedStatement.executeLargeBatch();
            preparedStatement.clearBatch();
        }

    }

    private List<User> getBatchUsers(List<User> users, int current, int count) {
        return PageUtil.startPage(users, current, count);
    }


    private int calTimes(int count, int size) {
        return size % count == 0 ? size / count : size / count + 1;
    }
}
