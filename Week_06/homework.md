### 基于电商交易场景（用户、商品、订单），设计一套简单的表结构，提交 DDL 的 SQL 文件到 Github（后面2周的作业依然要是用到这个表结构）。

```mysql
-- user
create table `t_user`(
    `id` bigint unsigned not null auto_increment,
    `nickname` varchar(64) not null ,
    `mobile` char(11) not null ,
    `status` tinyint unsigned not null default 1 comment '1:正常 0:禁用',
    `avatar` varchar(256) default  null,
    `password` varchar(128) not null comment '密码,md5加密',
    `created_time` datetime not null,
    `updated_time` datetime not null,
    `created_by` varchar(256) not null,
    `updated_by` varchar(256) not null,
    primary key (`id`)
)comment 'user';

create  table `t_user_address`(
    `id` bigint unsigned not null auto_increment,
    `user_id` bigint unsigned not null,
    `province` char(4) not null,
    `city` char(4) not null,
    `region` char(4) not null,
    `detail` varchar(256) not null comment '详细地址',
    `tag` varchar(16) default  null comment '标签 如: home, company',
    `created_time` datetime not null,
    `updated_time` datetime not null,
    `created_by` varchar(256) not null,
    `updated_by` varchar(256) not null,
    primary key (`id`)
)comment 'user address';


create table `t_category`(
    `id` bigint unsigned not null auto_increment,
    `parent_id` bigint unsigned default null comment '父级分类id',
    `name` varchar(64) not null  comment '分类名称',
    `created_time` datetime not null,
    `updated_time` datetime not null,
    `created_by` varchar(256) not null,
    `updated_by` varchar(256) not null,
    primary key (`id`)
)comment '类目';


-- goods
create table `t_goods` (
    `id` bigint unsigned not null auto_increment,
    `category_id` bigint unsigned not null comment '类目id',
    `title` varchar(256) not null,
    `desc` varchar(512) not null,
    `image_url` varchar(512) not null,
    `created_time` datetime not null,
    `updated_time` datetime not null,
    `created_by` varchar(256) not null,
    `updated_by` varchar(256) not null,
    primary key (`id`)
) comment 'goods';

-- sku
create table `t_goods_sku`(
    `id` bigint unsigned not null auto_increment,
    `goods_id` bigint unsigned not null ,
    `stock` int unsigned  not null,
    `price` int unsigned not null ,
    `created_time` datetime not null,
    `updated_time` datetime not null,
    `created_by` varchar(256) not null,
    `updated_by` varchar(256) not null,
    primary key (`id`)
)comment 'goods sku';

-- order
create table `t_order` (
    `id` bigint unsigned not null auto_increment,
    `order_no` varchar(32) not null,
    `order_status` tinyint unsigned not null comment '0:已取消 1:已完成',
    `pay_status` tinyint not null comment '0: 未支付 1:已经支付 2:已退款',
    `fail_reason` varchar(64) default '' comment '失败原因',
    `created_time` datetime not null,
    `updated_time` datetime not null,
    `created_by` varchar(256) not null,
    `updated_by` varchar(256) not null,
    primary key (`id`)
)comment  'order';


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
    `created_by` varchar(256) not null,
    primary key (`id`)
)comment 'order detail';

```

