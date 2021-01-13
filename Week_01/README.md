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




