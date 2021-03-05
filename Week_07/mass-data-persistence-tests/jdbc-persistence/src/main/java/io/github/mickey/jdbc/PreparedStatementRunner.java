package io.github.mickey.jdbc;

import io.github.mickey.jdbc.config.ConnectionManager;
import io.github.mickey.jdbc.dataprepared.MassUserGenerator;
import io.github.mickey.jdbc.domain.User;
import io.github.mickey.jdbc.persistence.NativeSQLWithPreparedStatement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PreparedStatementRunner {

    public static void main(String[] args) throws SQLException, InterruptedException {
        int total = 1000000;
        Connection c1 = ConnectionManager.getConnection();
        Connection c2 = ConnectionManager.getConnection();
        Connection c3 = ConnectionManager.getConnection();
        Connection c4 = ConnectionManager.getConnection();
        NativeSQLWithPreparedStatement ns1 = new NativeSQLWithPreparedStatement(c1);
        NativeSQLWithPreparedStatement ns2 = new NativeSQLWithPreparedStatement(c2);
        NativeSQLWithPreparedStatement ns3 = new NativeSQLWithPreparedStatement(c3);
        NativeSQLWithPreparedStatement ns4 = new NativeSQLWithPreparedStatement(c4);
        List<User> batch1 = MassUserGenerator.generate(total / 4);
        List<User> batch2 = MassUserGenerator.generate(total / 4);
        List<User> batch3 = MassUserGenerator.generate(total / 4);
        List<User> batch4 = MassUserGenerator.generate(total / 4);

        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CountDownLatch cd = new CountDownLatch(4);
        executorService.execute(() ->{
            try {
                ns1.insertBatch(batch1);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                cd.countDown();
            }
        });
        executorService.execute(() ->{
            try {
                ns2.insertBatch(batch2);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                cd.countDown();
            }
        });

        executorService.execute(() ->{
            try {
                ns3.insertBatch(batch3);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                cd.countDown();
            }
        });
        executorService.execute(() ->{
            try {
                ns4.insertBatch(batch4);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                cd.countDown();
            }
        });



        cd.await();

        long end = System.currentTimeMillis();
        System.out.println("inserted " + total + " rows , spend " + (end - start) / 1000 + " seconds");
        executorService.shutdown();
    }
}
