package io.github.mickey.jdbc.persistence;

import io.github.mickey.jdbc.domain.User;
import io.github.mickey.jdbc.util.PageUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class NativeSQLWithStatement implements UserRepository {

    private final Connection connection;

    public NativeSQLWithStatement(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void batchInsert(List<User> users) throws SQLException {
        Statement statement = connection.createStatement();
        String insertSQL = "insert into t_user (nickname, mobile, status,avatar, password,created_time, updated_time) values";
        int count = 20000;
        int times = calTimes(count, users.size());
        for (int i = 1; i <= times; i++) {
            StringBuilder sql = new StringBuilder(insertSQL);
            List<User> currentBatch = getBatchUsers(users, i, count);
            for (User user : currentBatch) {
                sql.append(String.format("('%s','%s',%d,'%s','%s',now(),now()),", user.getNickname(),
                        user.getMobile(),
                        user.getStatus(),
                        user.getAvatar(),
                        user.getPassword()));
            }
            sql.deleteCharAt(sql.length() - 1);
            statement.addBatch(sql.toString());
            statement.executeBatch();
            statement.clearBatch();
        }
    }

    private List<User> getBatchUsers(List<User> users, int current, int count) {
        return PageUtil.startPage(users, current, count);
    }


    private int calTimes(int count, int size) {
        return size % count == 0 ? size / count : size / count + 1;
    }
}
