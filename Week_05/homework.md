### 1、（选做）使 Java 里的动态代理，实现一个简单的 AOP。

[https://github.com/xiaozefeng/JAVA-01/tree/main/Week_05/week5_homework/src/main/java/io/github/mickey/week5/aop](https://github.com/xiaozefeng/JAVA-01/tree/main/Week_05/week5_homework/src/main/java/io/github/mickey/week5/aop)

### 2、（必做）写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）, 提交到 Github。

[https://github.com/xiaozefeng/JAVA-01/tree/main/Week_05/week5_homework/src/main/java/io/github/mickey/week5/assemble](https://github.com/xiaozefeng/JAVA-01/tree/main/Week_05/week5_homework/src/main/java/io/github/mickey/week5/assemble)

### 3、（选做）实现一个 Spring XML 自定义配置，配置一组 Bean，例如： Student/Klass/School。

[https://github.com/xiaozefeng/JAVA-01/blob/main/Week_05/week5_homework/src/main/java/io/github/mickey/week5/assemble/examples/bean](https://github.com/xiaozefeng/JAVA-01/blob/main/Week_05/week5_homework/src/main/java/io/github/mickey/week5/assemble/examples/bean/Boss.java)


### 4.1 （挑战）讲网关的 frontend/backend/filter/router 线程池都改造成 Spring 配置方式；

**tag: 4.1**

[https://github.com/xiaozefeng/JAVA-01/blob/v4.1/Week_03/netty-gateway](https://github.com/xiaozefeng/JAVA-01/blob/v4.1/Week_03/netty-gateway/pom.xml)

### 4.2 （挑战）基于 AOP 改造 Netty 网关，filter 和 router 使用 AOP 方式实现；

tag: 4.3

### [https://github.com/xiaozefeng/JAVA-01/tree/v4.2/Week_03/netty-gateway](https://github.com/xiaozefeng/JAVA-01/tree/v4.2/Week_03/netty-gateway)

### 4.3 （中级挑战）基于前述改造，将网关请求前后端分离，中级使用 JMS 传递消息；

### 
### 4.4 （中级挑战）尝试使用 ByteBuddy 实现一个简单的基于类的 AOP；

### [https://github.com/xiaozefeng/JAVA-01/tree/main/Week_05/week5_homework/src/main/java/io/github/mickey/week5/bytebuddy](https://github.com/xiaozefeng/JAVA-01/tree/main/Week_05/week5_homework/src/main/java/io/github/mickey/week5/bytebuddy)

### 4.5 （超级挑战）尝试使用 ByteBuddy 与 Instrument 实现一个简单 JavaAgent 实现无侵入 下的 AOP。

[bytebuddy 和instrument实现agent的aop](https://github.com/xiaozefeng/JAVA-01/tree/main/Week_05/java-agent-example/agentx3)


# 第十课作业

## 1. （选做）总结一下，单例的各种写法，比较它们的优劣。

```java
// 简单
public class Singleton1 {
    private Singleton1() { }
    private static final Singleton1 instance = new Singleton1();
    public static Singleton1 getInstance() {
        return instance;
    }
}
// 懒加载，较为复杂
public class Singleton2 {
    private static volatile Singleton2 instance;
    private Singleton2() { }
    public Singleton2 getInstance() {
        if (instance == null) {
            synchronized (Singleton2.class) {
                if (instance == null) {
                    instance = new Singleton2();
                }
            }
        }
        return instance;
    }
}
// 简单
public class Singleton3 {
    private Singleton3() {}
    private static class SingletonHolder {
        private static final Singleton3 instance = new Singleton3();
    }
    public Singleton3 getInstance() {
        return SingletonHolder.instance;
    }
}
// 枚举实现最简单，且线程安全，防止反射，防止反序列化，最推荐的方式
public enum Singleton4 {
    INSTANCE
}
```
## 2. （选做）maven/spring 的 profile 机制，都有什么用法？

maven profiles 主要作用于编译期间，在编译时选择profile，编译完成已经确认了profile

Spring的profile支持启动的时候指定profile

两者支持的领域不太一样

maven profile 是将不同环境的配置打包进一个配置文件，应用程序去读取这个配置文件

spring profile 是用来装配不同环境的一个bean 或者不同的环境的一组bean

总结:  maven profile 在编译期生效， spring profile 在启动时生效

## 3. （必做）给前面课程提供的 Student/Klass/School 实现自动配置和 Starter。

[https://github.com/xiaozefeng/JAVA-01/tree/main/Week_05/spring-boot-homework3](https://github.com/xiaozefeng/JAVA-01/tree/main/Week_05/spring-boot-homework3)

## 4. （选做）总结 Hibernate 与 MyBatis 的各方面异同点。

**hibernate 和  mybatis 相同点:**

* 都是orm框架
* 都是基于jdbc做封装

**hibernate 和  mybatis 不同点**

hibername:

        * 通过面向对象的思想操作数据库，不懂SQL也能进行业务开发
        * 对DBA不友好，因为sql都是生成的
        * 一旦涉及到需要执行复杂SQL的项目， 几乎无法优化SQL

mybaits:

        * 基于xml, 原生sql的方式，仅仅是对象入参和返回做了封装
        * 缺点是繁琐: 可以通过mybatis-plus, mybaits-generator 减少繁琐的工作
        * 对DBA优化，因为SQL统一在一个地方管理，DBA可以审查这个目录，优化SQL逻辑

总结:

* hibernate适合单表操作的，简单，小型的项目, 因为能快速开发
* mybatis 更适合涉及到复杂SQL的项目，因为可以很方便的优化SQL, 目前除了很多优秀的项目，如 mybaits-generator, mybaits-plugs 可以极大简化单表操作，使得单表不需要写SQL，设计到复杂sql时，再使用xml方式定义复杂的sql
## 5. （选做）学习 MyBatis-generator 的用法和原理，学会自定义 TypeHandler 处理复杂类型。

## 
## 6. （必做）研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法： 1）使用 JDBC 原生接口，实现数据库的增删改查操作。 2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。 3）配置 Hikari 连接池，改进上述操作。提交代码到 Github。 附加题（可以后面上完数据库的课再考虑做）：

1）使用 JDBC 原生接口，实现数据库的增删改查操作[https://github.com/xiaozefeng/JAVA-01/blob/main/Week_05/jdbc-example/native-jdbc/src/main/java/io/github/mickey/jdbc/UserManagerX1.java](https://github.com/xiaozefeng/JAVA-01/blob/main/Week_05/jdbc-example/native-jdbc/src/main/java/io/github/mickey/jdbc/UserManagerX1.java)

2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作:[https://github.com/xiaozefeng/JAVA-01/blob/main/Week_05/jdbc-example/native-jdbc/src/main/java/io/github/mickey/jdbc/UserManagerX2.java](https://github.com/xiaozefeng/JAVA-01/blob/main/Week_05/jdbc-example/native-jdbc/src/main/java/io/github/mickey/jdbc/UserManagerX1.java)

3）配置 Hikari 连接池，改进上述操作:[https://github.com/xiaozefeng/JAVA-01/blob/main/Week_05/jdbc-example/native-jdbc/src/main/java/io/github/mickey/jdbc/UserManagerX3.java](https://github.com/xiaozefeng/JAVA-01/blob/main/Week_05/jdbc-example/native-jdbc/src/main/java/io/github/mickey/jdbc/UserManagerX1.java)


## 1. (挑战)基于 AOP 和自定义注解，实现 @MyCache(60) 对于指定方法返回值缓存60秒。

[https://github.com/xiaozefeng/JAVA-01/blob/main/Week_05/cache/](https://github.com/xiaozefeng/JAVA-01/blob/main/Week_05/cache/)

## 2. (挑战)自定义实现一个数据库连接池，并整合 Hibernate/Mybatis/Spring/SpringBoot。

## 
## 3. (挑战)基于 MyBatis 实现一个简单的分库分表+读写分离+分布式 ID 生成方案。

