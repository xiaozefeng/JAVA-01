# Week8 Summary



## ShardingSphere-proxy 实践

1. downlaod package

2. configuration

   server.yaml => 全局配置

   config-replica-query.yaml => 读写分离配置

   config-encrypt.yaml => 加密解密配置

   config-sharding.yaml => 分库分表配置

   config-shadow.yaml => 压测配置

3. Start server 

4. connect the proxy server

5. insert some thing and select some thing





读写分离场景下: 在一个事务中，写完读中不一致的问题，在解析sql如果一个请求是 insert/update, 
强制这个事务中的其他数据库操作走主库





## 扩展立方体

**X轴**: 通过clone整个系统，复制，集群

**Y轴**: 通过解耦不同的功能( 业务拆分)，按需复制, 

**Z轴**: 单个业务数据量太大，超过单体承受，水平拆分 +数据分片



## 为什么要数据库拆分

主从 + 读写分离 不能解决**数据库容量问题**



#### 解决容量问题的一些手段

1. 垂直拆分(库,表, 库+ 表) , 业务服务拆分 => 微服务
2. 水平拆分(库,表,库+表) + 数据分片 



shardinghere-jdbc 

shardinghere-proxy

这两者功能一致, jdbc是框架，proxy是中间件





## 数据迁移

#### 1. 全量迁移 

过程: 停机 => 导出数据 =>导入输入 => 切换数据源

优点是简单，不容易出问题，缺点是停机时间长



#### 2. 全量 + 增量

过程: 先迁移部分不会变动的数据， 再停机 导出可能会变动的数据，导入的目标库中，再切换数据源

优点是 相比第一种全量方式，时间上可以节省很多时间，但是缺点也很明显，迁移的数据必须要满足这种特性，否则就无意义

#### 3. binlong + 全量 +增量

过程: 通过工具模拟MySQL从库，再通过解析binlog做到读取所有存量数据，这个过程可以跟业务一起跑，不会影响线上业务，在升级时先检验两个库的数据是否一致，升级时直接切换数据源即可。

优点: 这个读取binlog的工具可以是一个中间件，在中间件可以做的很多事情了

- 可以做到非常短暂的停机时间

- 比如 数据要做转换，可以在中间件这里做
- 比如要做数据分库分表，数据分片等等



#### 工具

`shardingsphere-scaling`

- 支持数据全量和增量同步。
- 支持断点续传和多线程数据同步。
- 支持数据库异构复制和动态扩容。
- 具有 UI 界面，可视化配置。



