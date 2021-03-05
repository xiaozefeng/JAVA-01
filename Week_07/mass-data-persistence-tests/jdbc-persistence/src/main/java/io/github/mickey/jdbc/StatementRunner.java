package io.github.mickey.jdbc;

import io.github.mickey.jdbc.config.ConnectionManager;
import io.github.mickey.jdbc.dataprepared.MassUserGenerator;
import io.github.mickey.jdbc.domain.User;
import io.github.mickey.jdbc.persistence.NativeSQLWithStatement;
import io.github.mickey.jdbc.persistence.UserRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StatementRunner {

    private static final Integer total = Integer.parseInt(System.getProperty("total", "1000000"));
    private static final Integer batch = Integer.parseInt(System.getProperty("batch", "4"));

    public static void main(String[] args) throws SQLException, InterruptedException {
        Connection[] connections = prepareConnection(batch);
        UserRepository[] userRepositories = prepareUserRepository(batch, connections);
        List<List<User>> data = prepareData(total, batch);

        ExecutorService executorService = Executors.newFixedThreadPool(batch);
        CountDownLatch cd = new CountDownLatch(batch);

        long start = System.currentTimeMillis();
        for (int i = 0; i < batch; i++) {
            final int index = i;
            executorService.execute(() -> {
                try {
                    userRepositories[index].batchInsert(data.get(index));
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    cd.countDown();
                }
            });
        }
        cd.await();
        long end = System.currentTimeMillis();
        System.out.println("inserted " + total + " rows , spend " + (end - start) / 1000 + " seconds");
        executorService.shutdown();
    }

    private static List<List<User>> prepareData(int total, int count) {
        List<List<User>> res = new ArrayList<>();
        final int size = total / count;
        for (int i = 0; i < count; i++) {
            res.add(MassUserGenerator.generate(size));
        }
        return res;
    }

    private static UserRepository[] prepareUserRepository(int count, Connection[] connections) {
        UserRepository[] userRepositories = new UserRepository[count];
        for (int i = 0; i < count; i++) {
            userRepositories[i] = new NativeSQLWithStatement(connections[i]);
        }
        return userRepositories;
    }

    private static Connection[] prepareConnection(int count) {
        Connection[] connections = new Connection[count];
        for (int i = 0; i < count; i++) {
            connections[i] = ConnectionManager.getConnection();
        }
        return connections;
    }
}
