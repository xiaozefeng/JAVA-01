package io.github.mickey.jdbc;

import io.github.mickey.jdbc.config.ConnectionManager;
import io.github.mickey.jdbc.dataprepared.MassUserGenerator;
import io.github.mickey.jdbc.domain.User;
import io.github.mickey.jdbc.persistence.NativeSQLWithPreparedStatementWithMultiThread;
import io.github.mickey.jdbc.util.PageUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadPreparedStatementRunner {

    public static void main(String[] args) throws SQLException, InterruptedException {
        int total = 1000000;

        int times = 50;
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        List<User> users = MassUserGenerator.generate(total);
        CountDownLatch cd = new CountDownLatch(times);
        long start = System.currentTimeMillis();
        Connection connection = ConnectionManager.getConnection();
        NativeSQLWithPreparedStatementWithMultiThread ns = new NativeSQLWithPreparedStatementWithMultiThread(connection);
        for (int i = 1; i <= times; i++) {
            final int index= i;
            executorService.execute(() -> {
                try {
                    List<User> currentBatch = PageUtil.startPage(users, index, 20000);
                    ns.insertBatch(currentBatch);
                } catch (SQLException t) {
                    t.printStackTrace();
                }finally {
                    cd.countDown();
                }
            });
        }
        cd.await();
        long end = System.currentTimeMillis();
        System.out.println("inserted " + total + " rows , spend " + (end - start) / 1000 + " seconds");
        executorService.shutdownNow();
    }
}
