create table t_dict
(
    type_code varchar(30)  default '' not null comment '类型编码',
    code      int unsigned default 0  not null comment '编码',
    value     varchar(30)  default '' not null comment '值',
    primary key (type_code, code)
);

INSERT INTO t_dict (type_code, code, value) VALUES ('order_state', 1, '已付款');
INSERT INTO t_dict (type_code, code, value) VALUES ('order_state', 2, '已发货');
INSERT INTO t_dict (type_code, code, value) VALUES ('order_state', 3, '已完成');
INSERT INTO t_dict (type_code, code, value) VALUES ('order_type', 1, '销售订单');
INSERT INTO t_dict (type_code, code, value) VALUES ('order_type', 2, '采购订单');


create table t_order
(
    id           bigint auto_increment
        primary key,
    user_id      bigint       default 0  not null,
    state_code   int unsigned default 0  not null,
    type_code    int unsigned default 0  not null,
    product_code char(10)     default '' not null
);

INSERT INTO t_order (id, user_id, state_code, type_code, product_code) VALUES (1, 1, 1, 1, 'P0001');
INSERT INTO t_order (id, user_id, state_code, type_code, product_code) VALUES (2, 2, 2, 1, 'P0002');
INSERT INTO t_order (id, user_id, state_code, type_code, product_code) VALUES (3, 3, 2, 2, 'P0002');
INSERT INTO t_order (id, user_id, state_code, type_code, product_code) VALUES (4, 4, 2, 2, 'P0002');
INSERT INTO t_order (id, user_id, state_code, type_code, product_code) VALUES (5, 5, 3, 2, 'P0001');
INSERT INTO t_order (id, user_id, state_code, type_code, product_code) VALUES (6, 6, 3, 2, 'P0001');
INSERT INTO t_order (id, user_id, state_code, type_code, product_code) VALUES (7, 7, 3, 1, 'P0003');


create table t_product
(
    code  char(10)               not null
        primary key,
    name  varchar(30) default '' not null,
    price decimal(12, 2)         null
);

INSERT INTO t_product (code, name, price) VALUES ('P0001', '手机', 3000.00);
INSERT INTO t_product (code, name, price) VALUES ('P0002', '电脑', 5000.00);
INSERT INTO t_product (code, name, price) VALUES ('P0003', '单反', 8000.00);


create table t_user
(
    id       bigint auto_increment
        primary key,
    username varchar(30)  default '' not null,
    name     varchar(30)  default '' not null,
    phone    varchar(15)  default '' not null,
    address  varchar(100) default '' not null
);

INSERT INTO t_user (id, username, name, phone, address) VALUES (1, 'zhangsan', '张三', '13012340001', '北京');
INSERT INTO t_user (id, username, name, phone, address) VALUES (2, 'lisi', '李四', '13012340002', '上海');
INSERT INTO t_user (id, username, name, phone, address) VALUES (3, 'wangwu', '王五', '13012340003', '广州');
INSERT INTO t_user (id, username, name, phone, address) VALUES (4, 'zhaoliu', '赵六', '13012340004', '深圳');
INSERT INTO t_user (id, username, name, phone, address) VALUES (5, 'qianqi', '钱七', '13012340005', '天津');
INSERT INTO t_user (id, username, name, phone, address) VALUES (6, 'sunba', '孙八', '13012340006', '重庆');
INSERT INTO t_user (id, username, name, phone, address) VALUES (7, 'test7', '测试7', '', '');
INSERT INTO t_user (id, username, name, phone, address) VALUES (8, 'test8', '测试8', '', '');
INSERT INTO t_user (id, username, name, phone, address) VALUES (9, 'test9', '测试9', '', '');
INSERT INTO t_user (id, username, name, phone, address) VALUES (10, 'test10', '测试10', '', '');
