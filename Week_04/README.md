## 脑图总结

![Java-Concurrent](https://gitee.com/xiaozefeng/images/raw/master/pic/20210206205523.png)

##  锁

锁是用来保证 并发**互斥性**的，表示同一时间只有一个先吃可以访问互斥的共享资源。



### Synchronized 与 Lock 的区别？

synchronized 的实现是通过JVM实现的，本质是对 **管程**的实现。

每个对象的头部信息中包含锁的标识位，当一个线程获取到锁时，其实是操作锁对象的对象头。



Lock 是 JDK API的实现，相比于 synchronized多了很多新的特性

- lock支持被中断
- 支持多个等待队列 (condition),  synchronized 只支持一个
- 支持通过非阻塞的方式获取锁，支持超时获取锁， synchronized不支持。
- 支持指定锁  是否公平



## 并发原子类

### 乐观锁

假想每次访问共享变量时都没有人来争夺，因此不加锁

原子类采用的是无锁算法， 通过CPU提供的CAS + volatile关键字 保证原子性。



### 无锁算法 和 有锁算法的区别？

**有锁**:  有锁算法在并发高的时候，比无锁算法好

**无锁**:  无锁算法在并发低的时候因为不需要加锁，因此效率比 有锁高， 但是在并发高的时候，因为由于线程竞争会导致长时间自旋，反而效率更低。



## 并发工具

Semaphore: 控制同一时间并发访问的线程数

CountDownLatch: 等待一个或者多个线程执行执行，直到计数器到达0

CyclicBarrier: 多个线程相互等待，直到达到一个共同的屏障，相比CountDownLatch， CyclicBarrier可循环执行

Future & FutureTask & CompletableFuture : 对并发任务和并发任务关系的抽象



### 并发容器

ConcurrentHashMap: HashMap的并发容器

CopyOnWriteArrayList: ArrayList的并发容器



### 并发最佳实践

- 永远只在更新对象的成员变量时加锁
- 永远只在更新可变的成员变量时加锁
- 永远不在调用其他对象的方法时加锁





