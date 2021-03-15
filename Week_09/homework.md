# First
## 1、(选做)实现简单的Protocol Buffer/Thrift/gRPC(选任一个)远程调用demo。

## 2、(选做)实现简单的WebService-Axis2/CXF远程调用demo。
## 3、(必做)改造自定义RPC的程序,提交到github:
    1)尝试将服务端写死查找接口实现类变成泛型和反射
    2)尝试将客户端动态代理改成AOP,添加异常处理
    3)尝试使用Netty+HTTP作为client端传输方式
##4、(选做☆☆)升级自定义RPC的程序:
    1)尝试使用压测并分析优化RPC性能
    2)尝试使用Netty+TCP作为两端传输方式
    3)尝试自定义二进制序列化
    4)尝试压测改进后的RPC并分析优化,有问题欢迎群里讨论
    5)尝试将fastjson改成xstream
    6)尝试使用字节码生成方式代替服务端反射


# Second
## 1、(选做)按课程第二部分练习各个技术点的应用。
## 2、(选做)按dubbo-samples项目的各个demo学习具体功能使用。
## 3、(必做)结合dubbo+hmily,实现一个TCC外汇交易处理,代码提交到github:
    1)用户A的美元账户和人民币账户都在A库,A使用1美元兑换7人民币;
    2)用户B的美元账户和人民币账户都在B库,B使用7人民币兑换1美元;
    3)设计账户表,冻结资产表,实现上述两个本地事务的分布式事务。
## 4、(挑战☆☆)尝试扩展Dubbo
    1)基于上次作业的自定义序列化,实现Dubbo的序列化扩展;
    2)基于上次作业的自定义RPC,实现Dubbo的RPC扩展;
    3)在Dubbo的filter机制上,实现REST权限控制,可参考dubbox;
    4)实现一个自定义Dubbo的Cluster/Loadbalance扩展,如果一分钟内调用某个服务/提供者超过10次,则拒绝提供服务直到下一分钟;
    5)整合Dubbo+Sentinel,实现限流功能;
    6)整合Dubbo与Skywalking,实现全链路性能监控。