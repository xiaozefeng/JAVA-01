package io.github.mickey.dynamic.datasource.v2.context;

import lombok.extern.slf4j.Slf4j;

/**
 * @author mickey
 * @date 3/6/21 18:07
 */
@Slf4j
public class DataSourceRoutingContext implements AutoCloseable {

    public static final String MASTER = "masterDataSource";

    public static final String SLAVE = "slaveDataSource";

    private static final ThreadLocal<String> threadLocalDataSourceKey = new ThreadLocal<>();

    public static String getRoutingKey() {
        final String dataSourceKey = threadLocalDataSourceKey.get();
        return dataSourceKey == null ? MASTER : dataSourceKey;
    }

    public DataSourceRoutingContext(String datasourceKey) {
        log.info("set datasource key:{}", datasourceKey);
        threadLocalDataSourceKey.set(datasourceKey);
    }


    @Override
    public void close() throws Exception {
        threadLocalDataSourceKey.remove();
    }
}
