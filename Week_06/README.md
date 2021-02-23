## Java Lambda

函数式编程方法:   lambda = 匿名内部类

**只有一个非默认方法**的接口可以转换成 lambda 的写法，如:

Runnable  run()

Callable   T call()

Java 的类库提供了接口满足日常开发

Consumer ->  void accept(T t)

Supplier ->  T get()

Function ->  R apply(T t )

Predicate -> boolean test(T t)

**最终目的是为了简化开发,写更少的代码做更多的事**

## Java Stream

来自数据源的元素队列

filter

distinct

limit

skip

map

reduce

allMatch

anyMatch

noneMatch

findFirst

findAny

count

max

min

sorted

sorted(Comparable c)

collect

toList

toSet

toMap

toColleciton

forEach

多个操作之间可以合并，支持短路

### Parallel Stream

内部维持了一个线程池去跑任务

## Guava

Google 提供的 Java类库

减少错误，提高效率, Google内部的和核心库的Java 最佳实践

* Connections
* Files
* Cache
* Strings
* Rate Limiter
## Lombok

@Data

@Sl4j

@Getter

@Setter

@ToString

@Log

@Builder

@AllArgContructor

@NoArgContructor

## 
## SPI

Service Provider Interface

1. 在resources目录下创建 META-INF/services
2. 在services 目录下创建 接口的全类名的文件, 在文件中写入 接口的实现类
3. ServiceLoader.load(XXXInterface.class) 就能加载到


## Unit Tests

单元测试是保证代码质量的基本手段。

因为常见的业务开发都是都会用到数据库，缓存，用好Mock是关键

单元测试最好不要依赖任何外部系统


## DB

### MySQL

一般的业务系统瓶颈基本都在数据库层面，而大部分业务都是用的关系型数据库

在国内，关系数据库中的佼佼者就是MySQL

MySQL被Oracle收购后出了 8.0版本，但是用的人不多, MySQL的版本目前主流用 5.7版本

#### 逻辑架构

![图片](https://gitee.com/xiaozefeng/images/raw/master/pic/20210223173715.png!thumbnail)

**连接器**: 连接管理，权限校验

**查询缓存**: 开启了查询缓存时，先到查询缓存查找，找到直接返回，没找到就执行下面的流程，并把结果放到查询缓存，**但是这个表上的更新语句会把这个表的所有查询缓存清空，不建议使用查询缓存，收益太小。**

**分析器：**此法分析，语法分析

**优化器:**选择更优的查询方案

**执行器:**选择**引擎执行语句**

#### 日志

redo log -> innodb 专属:  每次更新不是直接写磁盘，而是写到redo log, 等空闲或者redo log写满了之后写到磁盘，并清控redo log

redo log 拥有crash-safe的能力，就算是MySQL宕机了，通过 redo log 可以恢复

binlog -> Server层实现:  逻辑日志

**redo log 和 binlog的区别:**

* binlog 是Server层实现，所有引擎都能用， redo log 是innodb 专属，只能innodb用
* redo log 是物理日志: 记录在数据页上做了什么修改,  binlog是逻辑日志: 记录这个这个语句的原始逻辑
* redo log 是循环写，固定空间会用完， binglog是追加写，写到一定大小后换一个文件写。
#### 两阶段提交

为了保证redolog 和binlog 一致性

#### 引擎

InnoDB:  支持行锁，事务， crash-safe, 外建

#### SQL执行的顺序

1. from
2. on
3. join
4. where
5. group by
6. having + 聚合函数
7. select
8. order by
9. limit

**实际上优化器可能会优化这个顺序**


#### **索引原理**

B+树: 树的层级低，减少磁盘IO

**为什么一个表的大小不建议超过2000w条数据？**

数据太多可能导致B+树的层次超过3，大大减低查询效率

#### 参数优化

* 连接参数
* 缓冲区参数
* innoDB参数
#### 设计优化  Best Practices

* 如何恰当选择引擎？**按照公司规范统一取**
* 库表如何命名？**按照公司规范统一**
* 如何选择恰当数据类型：**尽量小**
* char、varchar 的选择:**定长用char ，不定长只能用varchar**
* （text/blob/clob）的使用问题？**尽量不用**
* 时间日期的存储问题？**datetime用业务系统的时间**
* 文件、图片是否要存入到数据库？**使用分布式文件系统存储，存一个 url地址**
* 数值的精度问题？**用整形表示 或者用两个字段来存 整数部分和小数部分**
* 是否使用外键、触发器？**在业务系统中处理，不使用**
* 唯一约束和索引的关系？**唯一约束是索引**
* 是否可以冗余字段？**当然可以**
* 是否使用游标、变量、视图、自定义函数、存储过程？**尽量不用**
* 自增主键的使用问题？**分布式情况下不好，非分布式情况是最佳实践**
* 能够在线修改表结构（DDL 操作）？**可以，但是要评估时间**
* 逻辑删除还是物理删除？**逻辑删除**
* 要不要加 create_time,update_time 时间戳？**一定要加**
* 数据库碎片问题？**找时间窗口做**
* 如何快速导入导出、备份数据？**jdbc批量 或者  mysql 命令工具 或者 主从同步**





