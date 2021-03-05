package io.github.mickey.jdbc.persistence;

import io.github.mickey.jdbc.domain.User;

import java.sql.SQLException;
import java.util.List;

/**
 * @author mickey
 * @date 3/5/21 21:33
 */
public interface UserRepository {
    void batchInsert(List<User > users) throws SQLException;
}
