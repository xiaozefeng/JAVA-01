### **1.（选做）**用今天课上学习的知识，分析自己系统的 SQL 和表结构

```mysql
-- user
create table `t_user`(
    `id` bigint unsigned not null auto_increment,
    `nickname` varchar(64) not null ,
    `mobile` char(11) not null ,
    `status` tinyint unsigned not null default 1 comment '1:正常 0:禁用',
    `avatar` varchar(128) default  null,
    `password` varchar(32) not null comment '密码,md5加密',
    `created_time` datetime not null,
    `updated_time` datetime not null,
    primary key (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment 'user';

create  table `t_user_address`(
    `id` int unsigned not null auto_increment,
    `user_id` bigint unsigned not null,
    `province` char(4) not null,
    `city` char(4) not null,
    `region` char(4) not null,
    `detail` varchar(256) not null comment '详细地址',
    `tag` varchar(16) default  null comment '标签 如: home, company',
    `created_time` datetime not null,
    `updated_time` datetime not null,
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment 'user address';


create table `t_category`(
    `id` int unsigned not null auto_increment,
    `parent_id` int unsigned default null comment '父级分类id',
    `name` varchar(64) not null  comment '分类名称',
    `created_time` datetime not null,
    `updated_time` datetime not null,
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '类目';


-- goods
create table `t_goods` (
    `id` bigint unsigned not null auto_increment,
    `category_id` int unsigned not null comment '类目id',
    `title` varchar(256) not null,
    `desc` varchar(512) not null,
    `image_url` varchar(512) not null,
    `created_time` datetime not null,
    `updated_time` datetime not null,
    primary key (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  comment 'goods';

-- sku
create table `t_goods_sku`(
    `id` bigint unsigned not null auto_increment,
    `goods_id` bigint unsigned not null ,
    `stock` int unsigned  not null,
    `price` int unsigned not null ,
    `created_time` datetime not null,
    `updated_time` datetime not null,
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment 'goods sku';

-- order
create table `t_order` (
    `id` bigint unsigned not null auto_increment,
    `order_no` varchar(32) not null,
    `order_status` tinyint unsigned not null comment '0:已取消 1:已完成',
    `pay_status` tinyint not null comment '0: 未支付 1:已经支付 2:已退款',
    `fail_reason` varchar(64) default '' comment '失败原因',
    `created_time` datetime not null,
    `updated_time` datetime not null,

    primary key (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment  'order';


-- order detail
create table `t_order_detail`(
    `id` bigint unsigned not null auto_increment,
    `order_no` varchar(32) not null,
    `goods_id` bigint unsigned not null,
    `sku_id` bigint unsigned not null ,
    `user_id` bigint unsigned not null,
    `gods_title` varchar(256) not null,
    `goods_desc` varchar(512) not null,
    `goods_image` varchar(512) not null,
    `goods_price` int unsigned  not null,
    `created_time` datetime not null,
    `updated_time` datetime not null,
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment 'order detail';
```

### **2.（必做）**按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率

https://github.com/xiaozefeng/JAVA-01/blob/main/Week_07/mass-data-persistence-tests/jdbc-persistence/README.MD

### **3.（选做）**按自己设计的表结构，插入 1000 万订单模拟数据，测试不同方式的插入效率



### **4.（选做）**使用不同的索引或组合，测试不同方式查询效率

### **5.（选做）**调整测试数据，使得数据尽量均匀，模拟 1 年时间内的交易，计算一年的销售报表：销售总额，订单数，客单价，每月销售量，前十的商品等等（可以自己设计更多指标）

### **6.（选做）**尝试自己做一个 ID 生成器（可以模拟 Seq 或 Snowflake）

### **7.（选做）**尝试实现或改造一个非精确分页的程序



### **1.（选做）**配置一遍异步复制，半同步复制、组复制

做了一遍异步复制:  https://xiaozefeng.github.io/#/mysql/MySQL%E4%B8%BB%E4%BB%8E%E5%A4%8D%E5%88%B6

### **2.（必做）**读写分离 - 动态切换数据源版本 1.0

#### 1.0版本

https://github.com/xiaozefeng/JAVA-01/blob/main/Week_07/write-and-read-separation/dynamic-datasource-v1/README.MD

#### 1.1版本

https://github.com/xiaozefeng/JAVA-01/blob/main/Week_07/write-and-read-separation/dynamic-datasource-v2/pom.xml

### **3.（必做）**读写分离 - 数据库框架版本 2.0

#### 多数据源配置 + shardingsphere 读写分离

https://github.com/xiaozefeng/JAVA-01/blob/main/Week_07/write-and-read-separation/shardingsphrere-jdbc-v1/pom.xml



### **4.（选做）**读写分离 - 数据库中间件版本 3.0

### **5.（选做）**配置 MHA，模拟 master 宕机

### **6.（选做）**配置 MGR，模拟 master 宕机

### **7.（选做）**配置 Orchestrator，模拟 master 宕机，演练 UI 调整拓扑结构