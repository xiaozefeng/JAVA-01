## 什么是高性能？

#### 高并发用户 Mass Concurrent Users

高用户量  != 高并发  =>  大量用户同时访问系统，才是高并发

#### 高吞吐 High Throughout

QPS, TPS  => 衡量系统吞吐

#### 低延迟 Low  Latency

金融 | 交易 系统对延迟非常敏感

#### RRT   和  Latency 的区别？

RRT =>  Request Response Timeout 请求响应时间

**Latency： 系统内部处理一次请求的时间**

**RRT:   网络延迟 + Latency**


#### **吞吐和延迟的关系**

**一般情况 延迟越低， 吞吐越高**

**反之，延迟高，吞吐不一定低**


#### wrk 中的并发用户数，延迟，吞吐

```shell
# -c  并发用户数 
# --latency  延迟分布, 平均延迟意义不大   => 其实是RRT， 因为wrk是客户端连接服务端肯定有网络延迟
wrk -c40 --latency xxx.com
# 结果中有 Requests/sec :  就是吞吐量
```



## 高性能带来了哪些问题？

* 建设复杂度
* 破坏性
* 复杂度
#### **如何应对？**

**稳定性建设 ->  混沌工程**

## IO 模型

同步阻塞IO是最简单的IO模型，但是性能不好

JDK 带来了 性能更好的NIO，但是JDK NIO API十分难用还容易出错，Bug又多还不愿意修复

于是有了 Netty,  Netty基于NIO，但是比JDK提供的NIO 做的更好，做的更好，API 也更好用。

### BIO 模型  (Thread-Per Connection)

类比酒店吃饭: 每个客户都有一个人接待，这个人从接客，下单，做菜，上菜，送客 ，相当于1对1 VIP， 但是如果人多了，酒店经营成本支撑不住。

对应计算机世界，每个客户端都连接都用一个线程去处理，但是线程的数量是有限的，不能无限创建，而且线程之间抢CPU时间片造成上线文切换会随着线程数量的增多而急剧恶化。

![图片](https://uploader.shimo.im/f/AAI1vahSqynBwdMX.png!thumbnail?fileGuid=VcYJD9Qg9vrRTW6d)


### Reactor模型

>Reactor 是一种开发模式:  模式的核心流程为:
>1. 注册感兴趣的事件
>2. 扫描是否有感兴趣的事件发生
>3. 事件发生时做出相应的处理

Cleint 中的  SocketChannel 注册了  op_connect, op_write, op_read 事件

Server 中的 ServerSocketChannel 注册了  op_accept 事件

Server 中的 SocketChannel 注册了 op_write , op_read 事件

#### 单线程 Reactor   (SingleThread)

类比饭馆吃饭，这个饭馆只有一个人，但是这个人要做所有事情 迎宾，下单，做菜，上菜，送客。在现实世界肯定是行不通的，如果这个人要生病或者请假，相当于饭馆不能经营了。

在计算机的世界里也是一样，如果这个线程挂了，相当于整个系统就挂了。

但是这在计算世界中是一种很重要的思想 -> 多路复用

多个客户端复用一个线程处理请求。因为这个线程是基于事件驱动的所以不会阻塞。

![图片](https://uploader.shimo.im/f/rbcVdYsGq8k91UrU.png!thumbnail?fileGuid=VcYJD9Qg9vrRTW6d)

```java
# Netty中实现 单线程 Reactor
NioEventLoopGroup group = new NioEventLoopGroup(1);
ServerBootstrap b = new ServerBootstrap();
b.group(group);
```
#### 
#### 多线程 Reactor  (MultiThread)

类比酒店, 之前经营规模小的时候可以用一个来做所有事， 随着经营规模的增加，请多个人大家一起来干。同时分工也更加明确，接客和送客是由接待员来做的，具体的 下单，做菜，端菜由其他人来做。

在计算机世界里，具体的业务使用 线程池去做的，同时线程池还有一个缓冲队列，进一步提高了系统吞吐量。

![图片](https://uploader.shimo.im/f/3cXoA37soSKhN9OM.png!thumbnail?fileGuid=VcYJD9Qg9vrRTW6d)

```java
# Netty中实现 多线程 Reactor
NioEventLoopGroup group = new NioEventLoopGroup();
ServerBootstrap b = new ServerBootstrap();
b.group(group);
```
#### 主从 Reactor (Multiple Reactor)

相比于 多线程 Reactor,  分工进一步细化，聘请几个专业的迎宾，只负责接待客人。

![图片](https://uploader.shimo.im/f/CSA22P1gYFLbW8Lm.png!thumbnail?fileGuid=VcYJD9Qg9vrRTW6d)

```java
NioEventLoopGroup boss = new NioEventLoopGroup(1);
NioEventLoopGroup worker = new NioEventLoopGroup();
ServerBootstrap b = new ServerBootstrap();
b.group(boss, worker);
```


## TCP 三次握手和四次挥手

### **三次握手**

#### **为什么需要三次握手，两次握手行不行？**

不行！ 本质上握手是为了在 不可靠的网络环境中建立可靠的连接，理论上 3次是最少次数。

如果两次握手就能建立连接:

cleint  发送报文给 server ， server 一接受到就建立连接， 由于网络环境的不可靠，如果这个连接在 client 端已经是被废弃的，那么server 就保持了一个无用的连接。(想一想这种连接过多会怎样 ?)

****![图片](https://uploader.shimo.im/f/LOsnwiG8uUtNO0HB.png!thumbnail?fileGuid=VcYJD9Qg9vrRTW6d)


### **四次挥手**

#### 为什么需要4次挥手?

跟TCP握手不同的是，断开连接之前，双方都有可能在发送数据，而四次挥手就是为了保证

双方数据都不再发送数据了再关闭

#### 主动关闭方为什么需要等待 2了MSL

>MSL Max Segment Lifetime 最长报文段寿命
>一个MSL 在 Linux 默认是 1 分钟 在 Windows 上默认是 2分钟

因为 client端的最后一条消息可能丢失，如果丢失，服务端就收不到客户端的响应，那么服务端一直没办法关闭了。

有了 2个MSL等待:  如果 server 收不到 cleint 的第四次挥手，会再发一次 第三次挥手，因为这个时候 client 在等待，所以能收到。

![图片](https://uploader.shimo.im/f/F2WHaKbLqsTiKkwP.png!thumbnail?fileGuid=VcYJD9Qg9vrRTW6d)


## TCP 粘包 & 半包问题

### 什么是粘包？

一次接受到多个完整的消息

### 为什么会粘包？

发送方包大小远小于套接字缓冲区大小  =>  小包合并发送

### 什么是半包？

分多次接受到不完整的消息

### 为什么会半包？

一个包大于缓冲区大小 或者大于 MTU限制，必须要拆包

>MTU  Max Transfer Unit 最大传输单元
### 粘包和半包的本质？

TCP是流式协议，就像流水一样，消息之间没有边界

像UDP就没有 粘包和半包的问题。因为每个消息都有明显的边界。

### 怎么解决 ？

规定消息的边界  ->  封帧

* 固定长度
* 指定分隔符
* 报文头，报问题   -> 最常用, 可拓展性最好
#### Netty 如何解决？

```plain
固定长度:  FixedLengthFrameDecoder
分隔符: DelimiterBasedFrameDecoder
读取指定字段: LengthFieldBasedFrameDecoder 和  LengthFieldPrepender
```

## Netty 原理 & 处理流程

### 启动流程

![图片](https://uploader.shimo.im/f/i6RyT6faZBjBQs2W.png!thumbnail?fileGuid=VcYJD9Qg9vrRTW6d)

### 处理流程

![图片](https://uploader.shimo.im/f/JGOfdEUwm0IdkMlG.png!thumbnail?fileGuid=VcYJD9Qg9vrRTW6d)


### 什么是一次解码器？

解码: 原始数据流 ->  用户数据流  ,  解决粘包和半包问题

### 什么是二次解码器？

用户数据流 ByteBuf ->  Java Object， 方便使用

### option 和 childOption 的区别?

option 针对于 ServerSocketChannel

childOption 针对于每个创建的客户端 SocketChannel

![图片](https://uploader.shimo.im/f/NpAVDlFboBbmfbKi.png!thumbnail?fileGuid=VcYJD9Qg9vrRTW6d)

### Channel Handler Inbound 和 Outbound 顺序

![图片](https://uploader.shimo.im/f/uI8AcKZ5n4bNDZrb.png!thumbnail?fileGuid=VcYJD9Qg9vrRTW6d)

**顺序评估**

```java
 ChannelPipeline p = ...;
 p.addLast("1", new InboundHandlerA());
 p.addLast("2", new InboundHandlerB());
 p.addLast("3", new OutboundHandlerA());
 p.addLast("4", new OutboundHandlerB());
 p.addLast("5", new InboundOutboundHandlerX());
```
从上到下是入站事件:  125  ,因为 3,4没有实现入站
从下到上是出站操作:  543, 因为12没有实现出站

## 

## 网关

### 流量网关 => 关注稳定与安全

* 全局流控
* 日志
* 安全 => SQL注入，黑白名单，证书加解密, 防止扫描
#### 典型代表

* openresty
* kong
### 业务网关 => 更好的提供服务

* 业务流控
* 服务降级与熔断
* 路由，负载均衡，灰度
* 业务聚合，服务发现
* 鉴权
* 缓存
* 协议转换
#### 典型代表

* zuul
* zuul2
* spring cloud gateway

## 并发

### 多线程的本质

* 提高操作系统资源利用率
### 多线程带来的问题？

#### 原子性

一个或者多个操作在CPU上执行不会被中断

**为什么会带来原子性问题？ => 线程切换**

**怎么解决？ =>  加锁 =>  一个资源只能被一个线程访问**

#### 可见性

在一个线程里改变了共享变量的值能立马被另外一个线程看到。

**为什么有可见性问题？=> CPU 缓存**

**怎么解决？=> 禁用缓存 =>  volatile**

#### 有序性

Java内存模型中，允许编译器和处理器对指令进行重排序，这种重排序的过程不会影响单线程执行的结果，但是在多线程环境下会应该并发程序的正确性。

**为什么会有有序性问题？**

* 指令重排序
* 乱序执行优化

**怎么解决？ =>   synchronized  和 Lock**

#### 小结

CPU缓存，线程切换，编译优化 都是为了提高性能。

和多线程的目的是一样的，但是确造成了问题。


### 怎么用好多线程？

#### 分工 (设计)

给线程分配任务

#### 协作 (线程间通讯)

多个线程在同一个机器上，可以类比于一个公司中的团队，要做好一件事，有可能需要相互协作

#### 必要时互斥 (锁机制)

一群人一起做事，有些共享资源同一时刻只能一个人使用 (打印机，会议室）



### 分工

分工主要是偏设计，相当于任务编排

### 协作

* Object 的  wait & wait(mills) 和 notify() notifyAll()
* t.join()
* volitale

### 互斥

* synchronized
* lock

本质还是利用了 管程

### 死锁问题

一组线程因为竞争资源而 互相等待，导致 永久阻塞的现象

#### 构成死锁的几个条件?

* 互斥
* 不可抢占
* 互相等待
#### 如何预防死锁？

* 一次抢占 所需要的所有资源
* 如果只抢占了部分资源，在要获取其他锁的时候，使用超时获取 lock.tryLock(), 或者 lock.tryLock(mills)
* 修改逻辑，破坏互相等待的条件


