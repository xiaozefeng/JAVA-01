# Week8 HomeWork

### 1.（选做）分析前面作业设计的表，是否可以做垂直拆分。

### 2.（必做）设计对前面的订单表数据进行水平分库分表，拆分 2 个库，每个库 16 张表。并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github。


#### 1. sql
```sql
create schema ds_01;

create table `ds_01`.`t_order_0`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_01`.`t_order_1`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_01`.`t_order_2`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_01`.`t_order_3`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_01`.`t_order_4`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_01`.`t_order_5`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_01`.`t_order_6`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_01`.`t_order_7`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_01`.`t_order_8`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_01`.`t_order_9`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_01`.`t_order_10`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_01`.`t_order_11`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_01`.`t_order_12`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_01`.`t_order_13`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_01`.`t_order_14`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_01`.`t_order_15`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

create schema ds_02;

create table `ds_02`.`t_order_0`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_02`.`t_order_1`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_02`.`t_order_2`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_02`.`t_order_3`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_02`.`t_order_4`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_02`.`t_order_5`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_02`.`t_order_6`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_02`.`t_order_7`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_02`.`t_order_8`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_02`.`t_order_9`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_02`.`t_order_10`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_02`.`t_order_11`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_02`.`t_order_12`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_02`.`t_order_13`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_02`.`t_order_14`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
create table `ds_02`.`t_order_15`(`order_id` bigint(20) NOT NULL AUTO_INCREMENT, `user_id` int(11) NOT NULL,`status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL, PRIMARY KEY (`order_id`) )engine=InnoDB  CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


```
#### 2. shardingsphere-proxy 配置

1. server.yaml
```yaml
authentication:
 users:
   root:
     password: 123456

props:
 max-connections-size-per-query: 1
 acceptor-size: 16  # The default value is available processors count * 2.
 executor-size: 16  # Infinite by default.
 proxy-frontend-flush-threshold: 128  # The default value is 128.
 proxy-transaction-type: LOCAL
 proxy-opentracing-enabled: false
 proxy-hint-enabled: false
 query-with-cipher-column: true
 sql-show: true
 check-table-metadata-enabled: false
```

2. config-sharding.yaml
```yaml
schemaName: sharding_db

dataSourceCommon:
  username: root
  password: 123456
  connectionTimeoutMilliseconds: 30000
  idleTimeoutMilliseconds: 60000
  maxLifetimeMilliseconds: 1800000
  maxPoolSize: 50
  minPoolSize: 1
  maintenanceIntervalMilliseconds: 30000

dataSources:
  ds_0:
    url: jdbc:mysql://127.0.0.1:3306/ds_01?serverTimezone=UTC&useSSL=false
  ds_1:
    url: jdbc:mysql://127.0.0.1:3306/ds_02?serverTimezone=UTC&useSSL=false

rules:
  - !SHARDING
    tables:
      t_order:
        actualDataNodes: ds_${0..1}.t_order_${0..15}
        tableStrategy:
          standard:
            shardingColumn: order_id
            shardingAlgorithmName: t_order_inline
        keyGenerateStrategy:
          column: order_id
          keyGeneratorName: snowflake
    defaultDatabaseStrategy:
      standard:
        shardingColumn: user_id
        shardingAlgorithmName: database_inline
    defaultTableStrategy:
      none:

    shardingAlgorithms:
      database_inline:
        type: INLINE
        props:
          algorithm-expression: ds_${user_id % 2}
          allow-range-query-with-inline-sharding: true
      t_order_inline:
        type: INLINE
        props:
          algorithm-expression: t_order_${order_id % 16}
          allow-range-query-with-inline-sharding: true

    keyGenerators:
      snowflake:
        type: SNOWFLAKE
        props:
          worker-id: 123
```

#### 3.代码
https://github.com/xiaozefeng/JAVA-01/blob/main/Week_08/datasource-horizontal-expand/src/test/java/io/github/mickey/datasource/horizontal/expand/DatasourceHorizontalExpandApplicationTests.java



### 3.（选做）模拟 1000 万的订单单表数据，迁移到上面作业 2 的分库分表中。

### 4.（选做）重新搭建一套 4 个库各 64 个表的分库分表，将作业 2 中的数据迁移到新分库。


### 1.（选做）列举常见的分布式事务，简单分析其使用场景和优缺点。

### 2.（必做）基于 hmily TCC 或 ShardingSphere 的 Atomikos XA 实现一个简单的分布式事务应用 demo（二选一），提交到 Github。

### 3.（选做）基于 ShardingSphere narayana XA 实现一个简单的分布式事务 demo。

### 4.（选做）基于 seata 框架实现 TCC 或 AT 模式的分布式事务 demo。

### 5.（选做☆）设计实现一个简单的 XA 分布式事务框架 demo，只需要能管理和调用 2 个 MySQL 的本地事务即可，不需要考虑全局事务的持久化和恢复、高可用等。

### 6.（选做☆）设计实现一个 TCC 分布式事务框架的简单 Demo，需要实现事务管理器，不需要实现全局事务的持久化和恢复、高可用等。

### 7.（选做☆）设计实现一个 AT 分布式事务框架的简单 Demo，仅需要支持根据主键 id 进行的单个删改操作的 SQL 或插入操作的事务。