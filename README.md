# Join2Single
- 本工程是一个`关联表查询`转换为`单表查询`的demo，通过反射、AOP、自定义注解实现
- 开发初衷
    - 简化CRUD，代码工具生成单表的CRUD，在查询下配置要翻译的字段即可完成开发，无需修改mapper中的SQL
    - 跨库或微服务间关联一些字段

## 原理
### 关联查询
- 在实际业务中一些表都会有创建人`create_user_id`这个字段，在显示给前端时，通常会关联用户表`t_user`查询出对应的`name`返回给前端
- JavaBean
```java
private Long createUserId;
private String createUserName;
```
- SQL
```sql
select t1.create_user_id createUserId, t2.name createUserName
from t_xxx t1
left join t_user t2 on t2.id = t1.create_user_id
```

### 转换为单表查询
- 将关联查询转换为两次单表查询，然后遍历赋值
1. 首先查询出要翻译的实体列表 `List<T> entityList = selectList()`
```sql
select create_user_id createUserId
from t_xxx
```
2. 根据`createUserId`，查询`t_user`表中的`name`，组装成Map结构
    - `Map<id, name> valueMap = new HashMap<>()`
```sql
select id, name
from t_user
where id in (...)
```
3. 实体遍历赋值
```java
for (T entity : entityList) {
    entity.setCreateUserName(map.get(entity.getCreateUserId()))
}
```

## 具体使用代码
- com.lhd.j2s.module.order.service.impl.OrderServiceImpl
```java
@DictTranslating({
        @DictTrans(typeCode = "order_status", readFieldName = "status"),
        @DictTrans(typeCode = "order_type", readFieldName = "type")
})
@RefTranslating({
        @RefTrans(type = RefTransType.USER, readFieldName = "userId", writeFieldNames = {"userName", "userPhone"}),
        @RefTrans(type = RefTransType.PRODUCT, readFieldName = "productCode", writeFieldNames = "productName")
})
@Override
public IPage<OrderListVo> pageOrder(BasePageQuery query) {
    // 业务逻辑
}
```
- 对应的关联查询
```sql
select t1.id,
       t1.user_id userId, t2.name userName, t2.phone userPhone, t2.address userAddress,
       t1.product_code productCode, t3.name productName,
       t1.status, t4.name statusName,
       t1.type, t5.name typeName
from t_order t1
 left join t_user t2 on t2.id = t1.user_id
 left join t_product t3 on t3.code = t1.product_code
 left join t_dict t4 on t4.value = t1.status and t4.type_code = 'order_status'
 left join t_dict t5 on t5.value = t1.type and t5.type_code = 'order_type'
```

## 自定义参照翻译处理器
- 实现接口`TransValueMapHandler`
  - `ProductTransValueMapHandler`
- 增加枚举值`RefTransType`
  - PRODUCT("Product")
- 在注解中指定参照类型
    - @RefTrans(type = RefTransType.PRODUCT, ... )
## 相关依赖
- `mybatis-plus 3.4` 持久层框架
- `mapstruct 1.4` 实体转换
- `swagger2 3.0` 接口文档，访问地址`http://localhost:8080/swagger-ui/`

## 运行准备
- IDEA安装`lombok`插件
- 修改application.yml中数据库信息
- 执行/db/init.sql

## 待支持
- 字典、常用参照做缓存处理
- 参照翻译可以指定处理器
- 优化反射效率
