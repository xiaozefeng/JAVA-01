## GC 日志解读与分析

#### GC参数

-Xloggc:gc.log-XX:+PrintGCDetails  -XX:+PrintGCDateStamps

#### 
#### GC分类

Young GC = Minor GC

Minor GC + Major GC =  Full GC


## 内存&线程 分析工具

#### heap

http://gceasy.io

GCViewer

#### stack

http://fastthread.op

### 对象内存结构

对象头 + 对象


**结论:  包装类型比基本数据类型占用更多的空间， 因为有对象头，能用基本数据类型，尽量用基本数据类型。**

**多维数组也是用对象表示的，所以尽量不要用多维数据， 用一维数据代替 (在不大量增加复杂度的情况下， 否则得不偿失）**

### 相关问题

#### OutOfMemoryError: Java heap space   -> 堆内存不够用

* 超出预期的流量访问
* 内存泄露

**结论**:**通过监控系统或者业务日志查看是不是流量太大导致，如果不是 可以通过**`jmap -histo $pid`**分析是不是有内存泄露**

#### OutOfMemoryError: PermGen space/OutOfMemoryError: Metaspace

加载的`class`太多, metaspace 放不下

-XX:MaxMeaSpaceSize=512m

XX:+CMSClassUnloadingEnabled

**结论: 增加 metaspace 大小**

#### OutOfMemoryError: Unable to create new native thread  -> 创建线程的数量达到上限

* 调整OS参数
* 降低 xss 参数的值
* **调整代码, 改变线程使用的方式**

**结论:  对于小型系统一般是线程使用的方式有问题**

#### 内存分析工具

* jhat
* eclipse mat
## JVM 调优

### 内存分配速率 (High Allocation Rate)

上一次垃圾收集之后，与下一次GC开 始之前的年轻代使用量，两者的差值除以时间,就是分配速率。

分配速率过高就会严重影响程序的性能，在JVM中可能会导致 巨大的GC开销。

**正常系统：分配速率较低 ~ 回收速率 -> 健康**

**内存泄漏：分配速率 持续大于 回收速率 -> OOM**

**性能劣化：分配速率很高 ~ 回收速率 -> 亚健康**

### 过早提升  (Premature Promotion)

#### 提升速率

用于衡量单位时间内从年轻代提升到 老年代的数据量。

major GC 不是为了短暂存活的对象而设计的，但是现在 majorGC 也要清理这些生命短暂的对象，会导致GC时间过长，严重影响系统吞吐量

#### 过早提升的表现

* 短时间频繁FullGC
* 每次FullGC后，Old区的使用率很低 (10% ~ 20%)， 说明清理了很多对象
* 提升速率接近分配速率
#### 如何解决

目标是要让**临时数据能在Young能放的下**

* 加载 Young区大小
* 减少每次批处理的数量
### GC 疑难问题排查

* 开启GC日志 & GC 日志分析
* 系统监控 & 链路追踪
* 分析业务日志
* 分析Dump文件
* 充分的测试

## 网络编程

#### Server01

```java
public static void main(String[] args) throws Exception{
ServerSocket ss = new ServerSocket(port);
while (true){
Socket socket = ss.accept();
process(socket);
}
}
```
单线程处理，只有处理完前一个连接，才能处理后一个连接，吞吐量下。
#### Server02

```java
public static void main(String[] args) throws Exception{
        ServerSocket ss = new ServerSocket(port);
        while (true){
            Socket socket= ss.accept();
            new Thread(() -> {
                process(socket);
            }).start();
        }
    }
```
每一个连接使用一个线程处理， 看似是效率变高了， 但是在并发高的情况下， 会创建大量线程，线程之间的切换会损耗大量的时间，所以整体吞吐量还不如 单线程模式。
#### Server03

```java
public static void main(String[] args) throws Exception{
        ExecutorService es = Executors.newFixedThreadPool(40);
        ServerSocket ss = new ServerSocket(port);
        while (true){
            Socket socket = ss.accept();
            es.execute(() ->  {
                process(socket);
            });
        }
    }
```
线程池模式， 这种模式规避了 Server01和Server02的缺点， 即采用了多线程，又使用了池化技术复用了线程，避免线程过多产生的上下文切换，吞吐比 Server01和Server02 都要高
但是由于 IO 模型的局限性，其吞吐量也十分有限。

**结论:   吞吐量 Server02 < Server01 < Server03**

### 几种IO模型对比

#### 阻塞，非阻塞，同步，异步

**阻塞，非阻塞：要不要等待**

阻塞:  发起系统调用，等待内核做完才返回结果，期间不能做其他事

非阻塞： 发起系统调用，内核马上返回一个结果，可以做其他事了

**同步，异步:**针对于数据来说

**同步**

        * 等待内核把数据准备好了，返回给你
        * 等待内核把数据准备好了， 通知你，你去拿数据 ->**信号驱动**

**异步:**发起系统调用，马上返回一个结果，内核把数据准备好了，复制到某个地方，通知你复制好了，直接使用吧。


**信号驱动和 异步的区别：**号驱动 IO 由内核通知我们何时可以开始一个IO操作，

异步IO模型由内核通知我们IO操作何时已经完成。


#### BIO，NIO，AIO

**BIO： 同步阻塞   ->****食堂排队吃饭**

**NIO： 同步非阻塞  ->****叫号模式， 点单，拿个号，等待被叫号**

**AIO： 异步非阻塞 ->****包厢模式, 点单，菜好了，服务员端到包厢**


## Netty

>Neety is an anynchronous event-driven network application framework for repid development of maintainable high performance protocol servers & clients

![图片](https://uploader.shimo.im/f/hIuWR5tv3wz5c2pg.png!thumbnail?fileGuid=tDRrHpVTgQDqHgcJ)

* 异步
* 事件驱动
* 网络应用框架
* 高性能
* 服务端 & 客户端
* TCP &UPD
* Better throughput, low latency   -> 高吞吐，低延迟
* Less resource consumption ->  低资源消耗
* Minimized unnecessary memory copy ->  减少不必要的内存拷贝
### 核心组件

#### Channel

**channel**是 连接到网络套接字, 能够进行**read、write、connect**和**bind**等**I/O**操作的组件。

channel 提供了以下能力:

* channel 当前状态  ( is it open?  is it connected?)
* channel 的配置信息
* channel 支持的操作  ( read, write, connect, bind)
* channel 相关联的 ChannelPipeline

Channle 的所有I/O操作都是异步的, 对channel的调用会返回一个 ChannelFuture , 通过 ChannelFuture 实例可以知道 请求的 I/O 操作是  成功，失败或者取消。

#### ChannelFuture

Channel的所有IO操作都是异步的，所以调用Channel操作会立马返回一个ChannelFuture,

一个ChannelFuture 要么是完成的，要么是没完成的， 当IO操作开始时，一个ChannelFuture被创建，这个时候状态是 未完成，当IO操作完成后， ChannelFuture 变成已完成，有三个状态  成功，失败，被取消。

如果要在ChannelFuture完成时获取结果通知，可以通过**addListener( GenericFutureListener)**注册一个 ChannelFutureListener






#### ChannelHandler

处理或者拦截 IO 操作，并将其转发到ChannelPipeline的下一个 ChannelHandler

**子类型:**

ChannelInboundHandler 处理入站事件

ChannelOutboundHandler 处理出站事件

更方便的两个子类型

ChannelInboundHandlerAdapter

ChannelOutboundHandlerAdapter

通过 ChannelHandlerContext,  ChannelHandler 可以和其所属的ChannelPipeline 进行交互，通过 context ， ChannelHandler 可以在上游或者下游传递事件，动态修改或者存储信息 (使用AttributeKeys)


#### ChannelPipeline

一个ChannelHandler的列表， 用于处理或拦截 Channel 的入站事件和出站操作，ChannelPipeline实现了 Intercepting Filter 模式的高级形式，使用户可以完全控制事件的处理方式，以及管道中ChannelHandler如何交互。

每个Channel 都有自己的 ChannelPipeline ，在Channel被创建的时候会自动创建相关联的 ChannelPipeline.

![图片](https://uploader.shimo.im/f/uI8AcKZ5n4bNDZrb.png!thumbnail?fileGuid=tDRrHpVTgQDqHgcJ)

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



### ByteBuf

NIO缓冲区的抽象

建议使用 Unpooled 创建ByteBuf

**特性:**

#### *Random Access Indexing  随机访问*

```java
 ByteBuf buffer = ...;
 for (int i = 0; i < buffer.capacity(); i ++) {
     byte b = buffer.getByte(i);
     System.out.println((char) b);
 }
```

#### *Sequential Access Indexing  顺序访问*

readerIndex 用于读操作

writerIndex 用于写操作

![图片](https://uploader.shimo.im/f/48JM9uTMWgeImaPN.png!thumbnail?fileGuid=tDRrHpVTgQDqHgcJ)

#### Readable byte (The Actual Content)

这一段存储了实际的数据，任何以 read 或者 skip 开头的方法都会增加 readerIndex的值。

新分配，包装，复制的 ByteBuf 默认的 readerIndex = 0。

```java
// 循环读取
// Iterates the readable bytes of a buffer.
ByteBuf buffer = ...;
while (buffer.isReadable()) {
System.out.println(buffer.readByte());
}
```
#### Writable bytes

顾名思义，Writable  bytes 可写字节数

任何以 write 开头的方法都会在 writerIndex处开始写入，并且writerIndex随之向后移动。

新创建的 ByteBuf 的 writerIndex 默认值为 0

包装或者复制的 ByteBuf 的writerIndex默认值为 ByteBuf的 capacity.

#### 
#### Discardable bytes

可丢弃的字节数  ->  已经被读过的字节数

初始情况下 长度为0， 随着 read 操作，长度不断变大

可以 discardReadBytes() 释放这段内存



