package io.github.mickey.dynamic.datasource.v2.context;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author mickey
 * @date 3/6/21 18:06
 */
public class DataSourceRouting extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceRoutingContext.getRoutingKey();
    }
}
