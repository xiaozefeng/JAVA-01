

## Spring 框架设计

### 什么是Spring框架？

Spring 出现的初衷是为了在 J2EE 领域替代 EJB， 其创始人为 Rod Johnson

这是其官网的一段话:  Why Spring?

Spring makes programming Java quicker, easier, and safer for everybody. Spring’s focus on speed, simplicity, and productivity has made it the[world's most popular](https://snyk.io/blog/jvm-ecosystem-report-2018-platform-application/)Java framework.

### 什么是框架?

**框是约束，架是支撑**

框架: 为解决一个开放性问题而设计的具有一定约束性的支撑结构。在此结构上可以根据具体问题扩展、安插更多的组成部分，从而更迅速和方便地构建完整的解决问题的方案。




## Spring Bean

### Spring Bean 的生命周期

1. 创建bean
2. 依赖注入
3. 初始化bean
4. 执行各种bean的销毁钩子函数
## Spring AOP

AOP 面向切面编程

Controller -> Service -> Dao 可以看做一个纵向， 切面就是在中间切开，植入一些自己的东西。

Spring AOP:  Spring因为管理了所有bean，可以在需要代理的类做一层包装，返回包装后的bean给用户。

这个包装可以是 JDK的动态代理，也可以是CGLib等技术

## 

## Spring XML 配置bean

Spring 读取xml 配置信息，解析成bean


## Spring Boot 核心原理

随着多年的发展， Spring变得越来越复杂，面临着和EJB一样的窘境，这时Spring Boot 应运而生，Sping Boot 是在Spring的基础上，采用**约定大于配置的思想,**极大简化了使用 Spring开发Java程序的过程，使的只需极少的配置就可以构建生产级别的应用，让程序员只需关注业务开发。

其核心原理

* @EnableXX + @Import
* 各种 @Contdition
* SPI技术
* 以及各种Starter
## Spring Boot Starter 详解

使用 Spring 提供的各种注解如:  @EnableXX + @Import + @Condition 以及依赖 Spring Boot 的核心原理 ，可以实现各种开箱即用的 starter

**自定义一个starter的步骤:**

1. 创建一个maven 项目**注意 :**artifactId 命名:  不要采用spring-boot官方命名规则， 而是使用 xxx-spring-boot-starter
2. 写入依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
</dependency>
```
3. 定义config 类， 标注 @Configuration 注解, 并注册一个bean,  HelloService, 这个service 使用一个方法， sayHello()
4. 在 resources 目录下创建 META-INF/spring.factories 文件，写入 properties格式的数据
```java
org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.mickey.x2.starter.config.MickeyConfig
```
key是固定的，值为你定义的配置的类的限定类名。
5. 在 Spring Boot 项目中引入 刚才定义的starter,  使用  @Autowire 注入 HelloService，即可使用。

以上就是定义一个最简单的 spring-boot-starter的过程。还有更复杂的玩法，如

1. 可以在SpringBoot项目中覆盖 starter中的配置
2. 在starter项目中，可以使用 各种 @Condition 来决定是否要返回bean
3. 或者引入starter后，是不是要开启，可以使用 @EanbleXXX + @Import注解

## JDBC 与数据库连接池

JDBC:  Java Database Connectivity 是 Java中用来规范客户端程序如何访问数据库的一套规范

JDBC 中的概念:

DriverManager:  数据库驱动加载

Conneciton:  一个数据库连接，一个Session

Statement:  SQL语句的表示

PreparedStatement:  表示预编译的SQL语句

ResultSet:  结果集

Savepoint: 事务回滚点


### 连接池

因为数据库能创建的连接**有限，**创建和销毁一个连接时间较长， 导致数据库连接是稀缺资源。

稀缺资源可以使用**池化思想**缓存起来，这样既提高了**访问速度，**又**降低了自愿开销，**变形提高了吞吐，降低了延迟。

我们使用数据连接的场景很简单，获取一个 Conneciton , 通过 Connection 获取 Statement或者PreparedStatement 执行 SQL，拿到执行的结果后，调用 Connection的 close()方法，关闭连接。

池化只需动到两步:

1. 获取连接时，从连接池中获取，返回一个代理对象
2. 调用close()时， 因为返回的是代理对象， 所以close可以改写成放回连接池。

## ORM

Object Relational Mapping 对象关系映射

由于Java是一门面向对象的编程语言，在程序员中使用类和对象表示万事万物，当需要将数据保存到数据库时，需要调用JDBC的接口写入。

此时的问题是:  能不能像操作对象一样操作数据库。通过建立 对象与 数据库表的映射关系。

### ORM框架

### Hibernate 和Mybaits总结比较

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


