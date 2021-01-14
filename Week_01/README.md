Java 是一门 面向对象，静态类型，编译执行，有VM和GC，跨平台的高级编程语言

Java 的跨平台是 二进制跨平台的， 也就是说编译成 class或者jar 文件 之后可以在跨平台运行，只要对应的操作系统安装了 JVM 虚拟机。


## 字节码

Java bytecode 是由单字节 (byte) 的指令组成，理论上可以支持 256 (byte长度为8  2的8次幂=256)  个操作码 ( opcode)

### 字节码生成与查看

```plain
javac XX.java
javap -c -verbose XX
# 生成带 package 的类
javac -d . XX.java
javap -c -verbose com.xx.xx.XX
```
### 字节码分类

JVM 字节码对照表:[https://www.cnblogs.com/tsvico/p/12708417.html](https://www.cnblogs.com/tsvico/p/12708417.html)

#### 栈操作，包括和局部变量的交互

* load 把局部变量压入栈 ，load_0 将一个本地变量压入栈
* store 从栈顶把值放入 局部变量 ,store_0 是将栈顶数据写入 第一个局部变量, iconst_m1 m1是 -1
* const_0 到 count_5 把常数 0-5 压入栈
* ldc 将常量池数据压入栈
* iaload , iastore 数组变量的入栈，出栈
* dup 复制栈顶数值，并入栈

![图片](https://uploader.shimo.im/f/Ap5NuErKsnBEGokB.png!thumbnail?fileGuid=hJ9jk3hgdQyvyyTw)

#### 程序流程操作指令

goto

ifeq, ifgt, ifge, iflt, ifle 拿栈顶元素与0比较

if_icompeq,  拿栈顶两个元素比较

athrow 抛出异常

#### 对象操作指令，包括对方法的调用

getstatic

putstatic

getfield

putfield

invokevirtual

invokespecial

invokestatic

invokeinterface

invokedynamic

instanceof

chekcast


new 创建一个对象, 并将其引用引用值压入栈顶

newarray 创建一个指定的原始类型(如int, float, char等)的数组, 并将其引用值压入栈顶

arraylength 数组长度

#### 算数运算，类型转换指令

add ,sub , mul, div, rem 取模，neg 取负

shl 左移， shr 右移

ior 按位或, iand 按位与

inc 自增

x2x 从某个类型转换成某个类型


## 类加载器

![图片](https://uploader.shimo.im/f/srRbLVP6LdQsdoNK.png!thumbnail?fileGuid=hJ9jk3hgdQyvyyTw)

### 触发初始化的情况

* 启动Java进程，main方法所在的类会初始化
* new 对象时
* 调用类的静态方法，或者静态字段时，会初始化类
* 子类初始化会触发父类的初始化
* 一个接口定义了 default 方法， 直接或者间接实现了这个接口的类初始化了，也会触发接口的初始化
* 反射某个类时
* 初次调用 MethodHandle， 对应的类会初始化
### 不会初始化的情况 (可能加载)

* 通过子类引用父类的静态字段，只会初始化父类， 不会初始化子类
* 定了对象数组，不会触发该类的初始化
* 常量在编译期间会存入调用类的常量池，使用常量不会触发该定义常量类的初始化
* 通过Class获取类名，不会触发初始化，如 Hello.class
* Class.forName("xxx", false) 不会触发初始化，会加载
* 通过 ClassLoader.loadClass() 只会加载类，不会初始化
### 类加载机制

![图片](https://uploader.shimo.im/f/HWeWcq8ERnf3dYTd.png!thumbnail?fileGuid=hJ9jk3hgdQyvyyTw)

#### 加载器的特点:

* 双亲委派
* 缓存加载
#### 这样做的好处

避免核心API被篡改

避免重复加载 (同一个类文件被不同加载器加载，会产生两个不同的类)

保证了Java程序的稳定运行

#### 打印类加载器加载了哪些类

```java
public class JvmClassLoaderPrintPath {
    public static void main(String[] args) {
    // 理论上是获取不到 BootstrapClassLoader的信息的，因为是通过C++
   // 实现的，但是开了个口子获取一些信息
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        System.out.println("启动类加载器");
        for (URL url : urls)
            System.out.println(" ==> " + url.toExternalForm());
        // extension class loader
        printClassLoader(JvmClassLoaderPrintPath.class.getClassLoader().getParent());
        // application class loader
        printClassLoader(JvmClassLoaderPrintPath.class.getClassLoader());


    }
    public static void  printURLForClassLoader(ClassLoader cl) {
        Object ucp = isSightField(cl, "ucp");
        Object path= isSightField(ucp, "path");
        ArrayList ps =(ArrayList) path;
        for (Object p : ps) {
            System.out.println(" ==> " + p.toString());
        }
    }

    private static Object isSightField(Object obj, String fieldName) {
        try {
            Field f = null;
            if (obj instanceof URLClassLoader) {
                f = URLClassLoader.class.getDeclaredField(fieldName);
            }else {
                f = obj.getClass().getDeclaredField(fieldName);
            }
            f.setAccessible(true);
            return f.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static void printClassLoader(ClassLoader cl) {
        if (cl!= null) {
            printURLForClassLoader(cl);
        }else {
            System.out.println("扩展类加载器" + " ClassLoad -> null ");
        }
    }
}
```
#### 添加类引用的几种方式

* 把jar包放到 jdk 的 lib/ext 下， 或者使用 -Djava.ext.dirs指定目录
* java -cp 或者 -classpath 或者 class 文件放到当前目录
* 自定义 ClassLoader加载
* 拿到当前执行类的ClassLoader ，反射拿到 addUrl 方法添加 jar 或者路径  JDK9无效
    * JDK9 添加的方式: Class.forName("xxx", new URLClassloader("dddd"));
## JVM 内存模型

JMM Java Memory Model

JMM 规范明确定义了不同线程之间，通过哪些方式，在什么时候可以看到其他线程保存到共享变量中的值，以及在必要时，如果对共享变量的访问进行同步。

这样做的好处是屏蔽了各种硬件平台和操作系统之间的内存访问差异，实现Java并发程序的真正跨平台。

![图片](https://uploader.shimo.im/f/P206yOawsz2VyOUQ.png!thumbnail?fileGuid=hJ9jk3hgdQyvyyTw)

JVM 粗略来看可以分为 线程共享和 非线程共享 两个区域

### 线程独占区域: Stack

每个线程都有自己的线程栈，A线程不能访问B线程的局部变量

所有 原生数据类型的局部变量都存放在栈中，因此不能被其他线程访问到

线程可以将一个变量传给另一个线程，但是不能共享该变量本身

### 线程共享区域: Heap

所有对象都存放在堆上 包括  (Long , Integer, Byte, String)

如果一个局部变量的类型是原生类型，那么所有数据都保存在栈中

如果一个局部变量的类型是 对象类型， 那么对象的引用保存在栈中，对象本身保存在堆中，

不管这个对象的成员变量是基本数据类型还是对象类型，都会保存在堆上

如果多个线程都能访问到某个对象的属性，每个线程都存有的是这个属性的拷贝(副本)



## JVM 启动参数

`-`开头: 标准参数

`-D`系统属性

`-X`开头: 非标准参数

`-XX`非稳定参数

-xx:+-Flags 开关值

-xx:key=vlaue

### 系统属性参数

-Dfile.encoding=UTF-8

-Duser.timezone=GMT+08

-Dmaven.test.skip=true

-Dio.netty.enentLoopThreads=8

可以通过  System.getProperty() 获取

System.setProperty() 设置

### 运行模式参数

* -Server:  启动慢，运行性能和内存管理效率高
* -Client:  启动快, 运行时性能和内存管理效率不高
* -Xlint: 解释模式运行，很慢
* -XComp: JVM一次把所有字节码编译成本地代码，从而带来最大程度的优化，需要预热
* -Xmixed: 混合模式，将解释模式和编译模式混合使用，由JVM决定合适使用什么模式

### 堆内存设置参数

* -Xmx: 最大堆内存
* -Xms: 初始堆内存大小
* -Xmn: 新生代堆大小
* -XX:MaxPermSize=xx  永久代大小， JDK1.7前使用
* -Xx:MetaspaceSize=xx  元空间大小，默认无限大
* -XX:MaxDirectMemorySize=xx
* -Xss: 线程栈大小
#### Questions

1. 如果不设置参数，默认值是什么

-server 运行模式: 在64位的机器上默认启用 -server模式

-mixed: 默认模式

查看默认堆内存大小:

```plain
java -XX:+PrintFlagsFinal -version | grep HeapSize
```
2. Xmx 和 Xms 最好设置成一样， 如果Xms 设置过小， 服务启动时会造成好几个 FullGC,

内存扩容时会造成性能抖动

3. Xmx 设置成机器的什么比例合适？

60% 到 80%

4. 画出 Xmx, Xms, Xmn , MetaSpace, DirectMemory, Xss 的关系

![图片](https://uploader.shimo.im/f/7O6OFOvJh728s9ny.png!thumbnail?fileGuid=hJ9jk3hgdQyvyyTw)

* Xmx 包含 xmn
* xss 和xmx 没有关系， 跟操作系统可用内存有关系，如果硬说 xmx 和xss的关系， xmx越大那么xss的可用就越小,  xmx 固定的情况下，xss设置的越小， 可用线程数就越多 (直到达到操作系统的限制)**公式:   (OS可用内存 -  xmx) / xss**
* DirectMemory 只和 操作系统内存大小相关

### GC 设置参数

查看默认GC

```plain
java -XX:+PrintCommandLineFlags -version
```
### ![图片](https://uploader.shimo.im/f/X6hkmw9b6YIKmuvh.png!thumbnail?fileGuid=hJ9jk3hgdQyvyyTw)

### 
### 分析诊断参数

* OOM时自动Dump堆内存:  java -XX:+HeapDumpOnOutOfMemoryError
* OOM时 Dump文件的路径:  -XX：HeapDumpPath
* 发生OOM时执行脚本: -XX：OnOutOfMemoryError
* 发生致命错误时执行脚本: -XX：OnError
* -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1506，远程调试
### JavaAgent 参数

Agent 是 JVM 中的一项黑科技, 可以通过无侵入方式来做很多事情，比如注入 AOP 代码，执行统 计等等，权限非常大。


## Java 命令行工具 &GUI工具

#### jps & jinfo

`jps`是列出 Java 进程，可以拿到`pid`

`jinfo`根据`pid`可以查看 Java 进程的`flags`,`command_line``system_properties`等信息

```shell
# jps
jps -l 
jps -mlv
# jinfo
jinfo -flags $pid
jinfo -sysprops $pid
jinfo $pid 
```
#### jstat

>jstat - Monitors Java Virtual Machine (JVM) statistics.   JVM 监控统计工具
```plain
jstat -options
//
-class 
-compiler 
-gc # GC统计信息, Byte
-gccapacity
-gccause
-gcmetacapacity
-gcnew
-gcnewcapacity
-gcold
-gcoldcapacity
-gcutil  # GC统计信息，百分比
-printcompilation
//
# 每 10000(interval) ms 统计一次，统计 1000(count) 次
jstat -gcutil $pid 10000 1000
jstat -gc $pid 10000 1000
 S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT
  0.00  46.86  47.62   2.02  95.19  92.25      1    0.037     1    0.177    0.215
S0: Survivor 0 
S1: Survivor 1
E : Eden
O: Old Generation
M: Metaspace
CCS: Compressed Class Space
YGC:  Young GC
YGCT:  YoungGC Time
FGC: FullGC
FGCT: FullGCTime
```
#### jmap

>jmap - Prints shard object memory maps or heap memory details for a process .  JVM堆内存分析工具

常用命令

```shell
# 打印内存情况
jmap -heap $pid 
# 打印对象统计情况
jmap -histo $pid 
# dump 堆内存
jmap -dump:format=b,file=xxx.hprof
```
#### jstatck

>jstack - Prints Java thread stack traces for a Java process.  Java线程分析工具

常用命令

```shell
# 输出线程相关的 lock 信息
jstack $pid -l 
```
#### jcmd

>jcmd - Sends diagnostic command requests to a running Java Virtual Machine (JVM)
>可以说jcmd是 jstat, jstack ,jmap 的整合命令
```shell
# 相当于 jps 
jcmd -l 
# 相当于是 jinfo 
jcmd $pid VM.version
jcmd $pid VM.flags
jcmd $pid VM.command_line
jcmd $pid VM.system_properties 
jcmd $pid Thread.print  # 相当于 jstack
jcmd $pid GC.class_histogram # 相当于 jmap -histo $pid
jcmd $pid GC.heap_info # 输出类似 jmap -heap $pid 的内容
```
### 图形化工具

#### jconsole & jmc & visualvm

jmc 和 visualvm 都单独安装比较靠谱， 不受版本限制

VisualGC 作为 IDEA 插件


## GC

**存在的意义?**

内存有限，所有程序要一起用，需要有人对内存进行管理， 让使用内存这件事更高效。

**如何标记一个对象存活？**

引用计数，当一个对象被引用时，计数+1 ，减少一个引用时 -1， 当没有被引用时可以被回收，但是可能存在**循环依赖的问题，比如A引用B ，B也引用A，那么A，B的引用一直不能为0，一直不会被回收 。**

**如何解决引用计数的问题呢？**

可达性分析算法, 从 "GC Root“ 对象作为起点，从这些节点向下搜索，节点走过的路径称之为引用链，当一个对象到 GC Root没有任何引用链，说明此对象不可用。

**哪些算是GC ROO 对象？**

* ****当前正在执行方法的参数和局部变量
* 活动线程
* 类的静态字段
* JNI引用
### GC 算法

**分代假设:**

设想大部分对象都是瞬时对象， 而那么存活长时间的对象， 可能存活更长时间。

基于此，将 heap 分为 young区 和 old区

young 存放新生对象，当对象存活时间够长了就移动到 old 区

**Mark-Sweep：**标记-清理

标记不需要被回收的对象，标记完成后统计回收没有被标记的对象

**优点:**简单，直接

**缺点:**会产生大量不连续的空间碎片

**Mark-Copy :**标记-复制

分为两块内存，A，B

假设对A进行清理，标记A区域的存活对象，复制到B区域，然后清空 A区域

**young区采用的都是mark-copy算法，因为young区大多是 瞬时对象，存活对象很少，使用mark-copy 算法，只需要很少的代价就可以完成 YGC。**

**优点:**内存连续, 当存活对象很少时，只需要很小的代价就可以完成一次YGC

**缺点:**需要一块额外的内存块

**Mark-Sweep-Compact：**标记-整理

标记不需要被回收的对象， 将其移动到一端，再清除另一端的内存。

**优点:**内存连续, 不需要额外空间

**缺点:**消耗更多的时间

**总结:**

**young区采用 Mark-Copy 算法，因为大部分是瞬时对象， 只许要花很小的代价就可以完成一个 YGC。**

**老年采用 Mark-Sweep / Mark-Sweep-Compact, 因为old区对象是长期存活对象， 如果采用 Mark-Copy ，可能没有那么多空间（或者说浪费空间) ，还有一个原因是 在 young区gc的时间，如果对象放不下了，会转移到 old 区（分配担保机制），如果old也采用 Mark-Copy算法的话，谁来给 old区担保呢？**

### GC过程

**YGC**

young 区分为  eden , s0,s1， 新生对象首先分配到 eden 区， eden区满了触发 ygc, 此时将eden存活的对象放入 s0 , s0和s1 交换， 下次 eden 再次满了之后，再次触发一次 ygc, 此时将eden存活的对象和 s0存活的对象，放入s1 ,再次交换 s0 和s1

如此反复 ygc， 当存活的对象达到一定的阈值 (MaxTenuringThreshold) ，对象将会移动到 old区， 或者对象在 s1 放不下了 ，也会转移到 old区 （分配担保机制）

**FGC**

对整个堆触发垃圾收集 ， 触发FGC 会触发一次 YGC


#### 运行中的程序对象之间的关系一直在变化，如何标记？

STW ： Stop The World

### SerialGC &ParNewGC & ParallelGC  & CMS & G1

#### SerialGC:

**-XX:+UseSerialGC**

**young区: mark-copy**

**old区: mark-sweep-compact**

**ygc 和 fgc 都会触发 STW**

**优点:**CPU利用率高， 适合客户端程序

**缺点 :**只能利用单核, 而且不用在大内存上用，想想一个线程清理几十G，上百G 需要多少时间

#### ParNewGC

-XX:+UseParNew 一般搭配 CMS 使用 ,  -XX:+UseConcMarkSweep

基于SerialGC 改良而成， 只用于 Young 区 , 除了可以利用**多核特性**，其他跟 SerialGC 一样

#### ParallelGC

JDK 8 默认 GC策略

ygc 和 fgc 都会触发 STW

young区采用 mark-copy ， old 区采用 mark-sweep-compact

-XX:ParallelGCThreads=Number 指定GC 线程数， 默认为CPU的核心数

在GC 期间，所有CPU核心都用来做辣鸡清理，比起SerialGC 只能利用单个CPU核心，ParallelGC总体暂停更少。

#### CMS

-XX:+UseConcMarkSweepGC

young取采用 mark-copy ， old区采用 mark-sweep, 不进行压缩， 使用 free-list 来标记空闲的空间。

CMS 设计的目标是避免在GC的时候出现**长时间**的卡顿，通过

1. 不对old区进行 compact， 使用 free-list 来管理内存空间的回收
2. mark-sweep 的多数阶段和 业务线程并发执行

**CMS GC 阶段**

* **Initial Mark****# STW  ：**标记所有跟对象，以及被跟对象直接引用的对象， 以及被young 区所有存活对象引用的对象
* **Concurrent Mark  # 从 initail-mark中的对象找起，标记所有存活对象**
* **Concrrent Preclean # 标记在此过程中对象关系变化的地方**
* **Final Mark****# STW**
* **Concurrent Clean**
* **Concurrent Reset**

**优点: 最少延迟**

**缺点:  内存碎片化, 在某些情况下可能造成不可预测的停顿，特别是内存较大的情况 (目前默认是G1，完全可以替代CMS）**

#### G1

-XX:+UseG1GC  -XX:MaxGCPauseMillis=50

G1: Garbage-First， 垃圾优先

G1 设计的目标: 将STW时间和分布，变成可预期和可配置的。

为了达到整个目标:

* G1不在分 young区和old区，而是分为 一个一个的 region, 每个region 可以是 Eden ，Survivor， Old。逻辑上 所有 eded + survivor 就是young 区， 所有old 就是 old区
* G1 会优先清理 垃圾最多的 region

G1 会通过运行情况不断调整GC策略， 以此控制暂停时间。JVM刚启动的时候没有收到什么信息，此时处于 fully-young模式，当Eden满了之后，STW， 将存活对象转移( Evacuation)到 Suivivor。

### GC组合

Serial 实现单线程低延迟

ParNew + CMS  实现多线程低延迟组合

Parallel 实现多线程高吞吐量


### 如何选择合适的GC策略？

**指导原则**

1. 吞吐优先 使用 ParallelGC
2. 低延迟 使用 CMS
3. 系统内存较大，GC时间希望可控 使用 G1 （4G以上为较大)
#### 各个版本默认GC

jdk8 默认为  ParallelGC

Jdk9+  默认为 G1




