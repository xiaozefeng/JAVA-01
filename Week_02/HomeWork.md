**Week02 作业题目（周三）：**

**1.（选做）**使用 GCLogAnalysis.java 自己演练一遍串行 / 并行 /CMS/G1 的案例。

**脚本准备:  gc.sh**

```shell
#!/bin/bash
java "-Xmx$2" "-Xms$2" "-XX:+Use$1GC" -XX:+PrintGCDetails -XX:+PrintGCDateStamps  GCLogAnalysis
```
**脚本准备 analysis.sh****分析时不打印GC日志  删除掉**-XX:+PrintGCDetails -XX:+PrintGCDateStamps
```shell
#!/bin/bash
number=$3
sum=0
for n in $(seq $number); do
	last=$(./gc.sh $1 $2 |awk -F: '{print $2}')
   	sum=$(($sum+$last))
done
echo "total:$sum"
echo "avg:$(($sum/$number))"
```
**使用:**

```shell
# 单独执行 gc 打印
./gc.sh Serial 1g
# 执行分析   使用 SerialGC 1g堆内存， 执行 2次
./analysis.sh Serial 1g 2
```
## 
## GC日志解读

**Xmx 1g Xms 1g    10次采样 1秒**

**Serial Avg: 18351**

**Parallel Avg:  18059**

**ConcMarkSweep Avg:  19773**

**G1 Avg: 19156**

**Xmx 2g Xms 2g    10次采样 1秒**

**Serial Avg: 17334**

**Parallel Avg:****21876**

**ConcMarkSweep Avg:  17535**

**G1 Avg: 18467**

**Xmx 4g Xms 4g    10次采样  1秒**

**Serial Avg:  1773**

**Parallel Avg:**22857

**ConcMarkSweep Avg:  18427**

**G1 Avg: 20215**



```plain
# YGC
2021-01-15T10:47:49.251+0800: [GC (Allocation Failure) [PSYoungGen: 262131K->43506K(305664K)] 262131K->78657K(1005056K), 0.0070467 secs] [Times: user=0.02 sys=0.02, real=0.00 secs] 
# FGC
2021-01-15T10:47:49.674+0800: [Full GC (Ergonomics) [PSYoungGen: 37199K->0K(232960K)] [ParOldGen: 597824K->333428K(699392K)] 635024K->333428K(932352K), [Metaspace: 2538K->2538K(1056768K)], 0.0502074 secs] [Times: user=0.23 sys=0.01, real=0.05 secs]
```
**结论:**

**堆内存 设置小了不好，因为内存少了，容易 ygc ，fgc，容易OOM**

**内存大了不好是因为:  一次GC花费的时间过长, 影响吞吐量**

**所以在不影响业务吞吐量的情况， 堆内存尽量小， 小步快跑**


**2.（选做）**使用压测工具（wrk 或 sb），演练 gateway-server-0.0.1-SNAPSHOT.jar 示例。

**脚本准备  run.sh**

```plain
#!/bin/bash
java "-Xmx$2" "-Xms$2" -XX:-UseAdaptiveSizePolicy "-XX:+Use$1GC" -jar gateway.jar
```
**使用**
```shell
./run.sh  Serial 1g
# SerialGC
java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseSerialGC  gateway-server-0.0.1-SNAPSHOT.jar
# ParallelGC
java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseParallelGC gateway-server-0.0.1-SNAPSHOT.jar
# ConcMarkSweepGC
java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseConcMarkSweepGC gateway-server-0.0.1-SNAPSHOT.jar
# G1GC
java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseG1GC gateway-server-0.0.1-SNAPSHOT.jar
```
#### GC情况

**SerialGC & ParallelGC & CMS**

一启动就是2次FGC

**G1GC**

启动后没有发生FGC

压测后 eden  , s0 , s1 交替变化, YGC 一直增长， FGC没动

#### Heap 情况

young区占用总堆内存的  1/3

old区占用总堆内存的  2/3

young区的

eden :  s0 : s1 是  8 :1 :  1的关系 (capacity）

jmap -histo $pid

压测时:   byte[] , int[], char[] 一直在增长



**4.（必做）**根据上述自己对于 1 和 2 的演示，写一段对于不同 GC 和堆内存的总结，提交到 GitHub。

**Serial GC: Young区使用mark-copy, Old区使用mark-sweep-compact, GC时会STW， 只能使用单核CPU， 适用于 client。**

**Parallel GC:   并行GC，Young区使用mark-copy, Old区使用mark-sweep-compact， GC时会STW，比起SerialGC， 能利用多核CPU的优势。适合后台计算，交互少的应用**

**CMS GC： Young区使用 mark-copy, Old区使用 mark-sweep。CMS 是以低延迟为设计目标的GC， 在CMS的多个阶段中，只有 Initial-Mark 和 Final-Remark 会 短暂 STW ，**

**适用于 Web服务端**

**G1 GC：Young区使用 mark-copy ，Old区使用 mark-sweep-compact,  G1: G1是一款以可控的GC停顿为目标的GC， G1 没有跟传统GC一样分为Young区和Old区， 而是分为一个一个的 region , 每个region 都可以是 Eden, Survior, Old， 可能一会是 Eden, 一会是 Surviori**

**一会是Old。 从逻辑上来分， 所有 Eden + Survior =  Young区， 所有 Old 加起来是 Old区。**

G1: Garbage First ， 垃圾优先， G1会优先清理 垃圾大的 region。

**结论:**

**JDK8 默认GC 是 ParallelGC**

**JDK9 ~ JDK15 默认GC是 G1**

**ServialGC 只在客户端程序使用即可**

**服务端程序: 追求最大吞吐使用 ParallelGC,  追求低延迟 使用 CMS,  内存够大使用 G1 (4g以上算大)**

**Week02 作业题目（周日）：**

**1.（选做）**运行课上的例子，以及 Netty 的例子，分析相关现象。

![图片](https://shimo-uploader-images.oss-cn-beijing.aliyuncs.com/uploader-cache/Kl3AyODDq2wSILpa.png/thumbnail?Expires=1611329476&OSSAccessKeyId=LTAI4FoEPTasjWkqu1meFaHK&Signature=uciHD1%2FnV9ieacEBCadW32g4WWw%3D&response-content-disposition=inline%3B+filename%3D%22image.png%22%3B+filename%2A%3DUTF-8%27%27image.png)

**8个连接，2个线程 1分钟  ，  JVM参数  -Xmx512m -Xms512m**

* 01版本的吞吐比 02版本要高，**这里不符合我的预期**.
        * 从GC统计信息中发现 02版本的YGC 达 4300 多次， 总的GC时间为 1.5秒,  01版本的YGC只有200多次， 总的GC时间为 1.2秒。 GC的时间差不太多。
        * 有一个猜测是 线程太多导致线程切换消耗了大量资源，反而吞吐量不如单线线程的01版本。
* 03版本的比 01 和 02 版本要高，这是符合预期的， 因为复用了线程
* Netty版本比 03要高，而且高很多， 这也是符合预期的， 因为IO模型不一样
## **1.（必做）**写一段代码，使用 HttpClient 或 OkHttp 访问[http://localhost:8801 ](http://localhost:8801/)，代码提交到 GitHub

```java
public class HttpClient {
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = "http://localhost:8801";
        String res = doGet(client, url);
        System.out.println(res);
    }
    private static String doGet(OkHttpClient client,
                                String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response res = client.newCall(request).execute()) {
            return res.body().string();
        }
    }
}
```


