package io.github.mickey.jdbc;

import io.github.mickey.jdbc.config.ConnectionManager;
import io.github.mickey.jdbc.dataprepared.MassUserGenerator;
import io.github.mickey.jdbc.domain.User;
import io.github.mickey.jdbc.persistence.NativeSQLWithStatement;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class NativeSQLWithStatementTest {


    @Test
    public void testInsertion() throws SQLException {
        int total = 5000;
        Connection connection = ConnectionManager.getConnection();
        NativeSQLWithStatement ns = new NativeSQLWithStatement(connection);
        List<User> users = MassUserGenerator.generate(total);
        long start = System.currentTimeMillis();
        ns.insertBatch(users);
        long end = System.currentTimeMillis();
        System.out.println("inserted " + total + " rows , spend " + (end - start) / 1000 + " seconds");
    }
}
