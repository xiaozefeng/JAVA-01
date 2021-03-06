package io.github.mickey.dynamic.datasource.v2.config;

import io.github.mickey.dynamic.datasource.v2.context.DataSourceRouting;
import io.github.mickey.dynamic.datasource.v2.context.DataSourceRoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mickey
 * @date 3/6/21 14:52
 */
@Configuration
public class AppConfig {


    @Bean("rwDataSourceProperties")
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties rwDataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean("roDataSourceProperties")
    @ConfigurationProperties("spring.second-datasource")
    public DataSourceProperties roDataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean("rwDataSource")
    public DataSource rwDataSource(@Autowired @Qualifier("rwDataSourceProperties") DataSourceProperties props) {
        return props.initializeDataSourceBuilder().build();
    }

    @Bean("roDataSource")
    public DataSource roDataSource(@Autowired @Qualifier("roDataSourceProperties") DataSourceProperties props) {
        return props.initializeDataSourceBuilder().build();
    }


    @Bean
    @Primary
    public DataSource primaryDataSource(@Autowired @Qualifier("rwDataSource") DataSource rw,
                                        @Autowired @Qualifier("roDataSource") DataSource ro) {
        Map<Object, Object> targetDataSourceMap = new HashMap<>();
        targetDataSourceMap.put(DataSourceRoutingContext.MASTER, rw);
        targetDataSourceMap.put(DataSourceRoutingContext.SLAVE, ro);
        DataSourceRouting datasource = new DataSourceRouting();
        datasource.setTargetDataSources(targetDataSourceMap);
        datasource.setDefaultTargetDataSource(rw);
        return datasource;
    }

    @Bean
    public PlatformTransactionManager txManager(@Autowired DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
