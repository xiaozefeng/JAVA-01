## Week04 作业题目（周三）

#### 1.（选做）把示例代码，运行一遍，思考课上相关的问题。也可以做一些比较。

思考写到总结里了

#### 2.（必做）思考有多少种方式，在 main 函数启动一个新线程，运行一个方法，拿到这个方法的返回值后，退出主线程? 写出你的方法，越多越好，提交到 GitHub。

https://github.com/xiaozefeng/JAVA-01/tree/main/Week_04/concurrency/src/main/java/io/github/mickey/concurrency/wait



## Week04 作业题目（周日）：

### 1.（选做）列举常用的并发操作 API 和工具类，简单分析其使用场景和优缺点。

#### Thread   vs   线程池技术

创建和销毁线程开销较大，线程池使用池化技术可以复用线程，避免线程频繁创建销毁，进而提高了性能。

但是线程池增加了复杂性，各种配置参数需要了解清楚，比如 核心线程数和最大线程数的设置，任务队里的选择，线程工厂的定义，达到线程池能处理的极限时的处理策略。

**在实际工程中，尽量不用Thread创建线程，而是使用线程池。**



#### synchronized   vs  Lock

synchronized 是从Java自带的关键字，可以实现**互斥锁**，在进入 synchronized 代码块前插入一条 monitor.enter 指令，退出 synchronized 块后插入一条  monitor.exit 指令实现。

Lock  是JDK通过API实现的， 实现了比 synchronized 更多的功能

- 锁可被中断
- 多个锁等待队列 (synchronized只支持一个)
- 实现了公平和非公平锁，synchronized只支持非公平锁
- 实现超时等待机制  tryLock(mills)   ，synchronized 是死等



但是 Lock的 lock 与 unlock 是需要显示调用的:

```java 
Lock l = ...
try {
 l.lock();
}finally{
  l.unlock();
}
```

**在实际工程中，按需使用，一般情况用 synchronized , 有需要多个等待队列的， 要实现非阻塞方式获取的**

**要实现公平锁的 ，这些情况，可以使用 Lock**.



#### 原子类(乐观锁)  vs 互斥锁 (悲观锁)

原子类是通过 CAS 机制实现的。CAS是CPU层面支持的原子性。是一种无锁算法。

**在并发小的情况，乐观锁比悲观锁性能好，因为不加锁，没有线程切换**

**但是在并发大的情况，乐观锁可能会一直自旋，性能甚至比悲观锁更差**



#### ReadWriteLock   vs     StampedLock

ReadWriteLock 和 StampedLock 都适用于  **读多写少的场景**

ReadWriteLock: 

- 允许多个线程同时读
- 只允许一个线程写
- 写的时候会阻塞读操作

StampedLock 比起 ReadWriteLock 更优秀的一点是 支持 **乐观读**



#### CountDownLatch  vs  CyclicBarrier

CountDownLatch

1. 可以实现一个线程，等多个线程执行完， 再唤醒当前线程

2. 也可以一个 计数器的 CountDownLath， 让多个线程等待 一个线程 countDown()。

CyclicBarrier 

1. 多个线程之间相互等待，直到所有线程都达到共同的 barrier(屏障) ，所有线程会被释放，计数器也会被重置
2. 1的这个过程可以重复，所以叫 cyclic



可以使用 CountDownLatch 实现  CyclicBarrier 的部分功能，也可以使用 CyclicBarrier 实现CountDownLatch的效果，但是最大的区别还是 CyclicBarrier是可以 **循环使用的**



#### 并发容器  vs  非并发容器  + 锁

除非有特殊的需求，否则无脑选择 **并发容器**，因为自己实现容易有bug





#### 更高层次的抽象 CompletableFuture , CompletionService

CompletableFuture 可以描述任务之间的关系, 如 串行关系、并行关系、汇聚关系。

CompletionService 可以批量执行多个异步任务。



## 2.（选做）请思考: 什么是并发? 什么是高并发? 实现高并发高可用系统需要考虑哪些 因素，对于这些你是怎么理解的?

**并发** 在计算机科学领域，并发是指 程序，算法或者问题的不同部分可以无序或部分有序地执行，而不会影响最终结果的正确性。

**高并发**：同一时间，海量用户同时访问, 它通常意味着 系统需要能同时处理许多请求。是不是高并发的系统有几个衡量指标：

- Response time 
- Throughput -> TPS (Transaction perseconds)
- QPS (Query per seconds)
- Number of concurrent users



#### 如何实现高并发？

##### 垂直扩展

- **硬件** 增加机器的配置，CPU核心数，内存大小，磁盘读写速度, 网卡传输性能
- **软件**： 使用缓存减少IO时间，使用异步编程增加吞吐量，使用无锁数据结构减少响应时间

**垂直扩展的缺陷是: 单机性能始终有限，而且达到一定的程序，成本会急速上涨， 但是对于创业初期，使用垂直伸缩是一个 速度快，成本低的方式**

##### 横向扩展

只要服务器增加，服务器的吞吐量可以成线性增长。

但是横向扩展会引入很多复杂性。需要做好横向扩展的架构设计。



**总结: 在小公司，一开始使用垂直伸缩的方式是性价比最高的，而随着业务量的增长，就有必要设计成可横向伸缩的架构了。**







## 3.（选做）请思考: 还有哪些跟并发类似 / 有关的场景和问题，有哪些可以借鉴的解决 办法。



## 4.（必做）把多线程和并发相关知识梳理一遍，画一个脑图，截图上传到 GitHub 上。 可选工具:xmind，百度脑图，wps，MindManage，或其他。

![Java-Concurrent](https://gitee.com/xiaozefeng/images/raw/master/pic/20210202203259.png)