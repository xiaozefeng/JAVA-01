### 2.（必做）按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率
mysql的连接的方式添加 rewriteBatchedStatements=true参数可以加快插入效率

分别测试了 Statement 和 PreparedStatement 的两种方式测试插入数据
把100w数据分为4份,用4个线程分别去插入

在Linux使用Docker起一个MySQL实例:

PreparedStatement的方式能到达 7s

Statement的方式 能到达10s




