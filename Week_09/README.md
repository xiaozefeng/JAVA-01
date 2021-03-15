# Week9 Summary

## RPC
> RPC is -> Remote Produce Call 像调用本地方法一样调用远程方法

> 核心是代理机制

#### Rules
1. remote: service producer
2. local: service consumer


#### RPC Process
1. Local proxy : Stub
2. Local serialization
3. Network communication
4. Remote deserialization
5. Remote proxy: Skeleton
6. Invoke actual business   
7. Remote serialization
8. Back to local
9. Local deserialization 


#### local and remote share
- first way: share 实体类，接口  => 某个语言专属
- second way : share 描述文件 根据描述文件可以在客户端生成调动代码 => 跨语言

#### Proxy 
1. AOP
2. JDK Dynamic Proxy 
3. ByteCode Enhancement

#### Serialization & Deserialization
1. 语言相关 => RMI
2. 语言无关 => Hessian, kyro => 优点效率高
3. 文件协议 => JSON, XML   => 优点易读

#### Network Communication Protocol 
- TCP
- HTTP


## Apache Duboo
Dubbo 是一款 高性能，轻量级，Java语言开发的RPC框架
- 高性能rpc
- 负载均衡
- 服务发现与注册
- 高度可拓展
- 服务治理

### Duboo Process
1. 启动容器
2. 生产者向注册中心注册自己
3. 消费者向注册中心发起订阅
4. 注册中心异步返回订阅结果
5. 消费者 调用 生产者
6. dubbo 统计调用








