# Join2Single
- 本工程是一个`关联表查询`转换为`单表查询`的demo
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
private <T> void trans(List<T> entityList, Class<T> entityType) {

        // 转换数据字典字段
        TransUtils.trans(entityList, entityType, new DictTransRule[]{
                // 字典编码，读写字段
                new DictTransRule("order_state", "stateCode"),
                new DictTransRule("order_type", "typeCode", "typeName")
        });
    
        // 转换其他表字段
        TransUtils.trans(entityList, entityType, new DBTransRule[]{
                new DBTransRule<>(
                        // 读写字段信息
                        new String[]{"userId", "userName", "userPhone", "userAddress"},
                        // 数据库查询信息
                        UserMapper.class,
                        new String[]{"id", "name", "phone", "address"}
                ),
                new DBTransRule<>(
                        new String[]{"productCode", "productName"},
                        ProductMapper.class,
                        new String[]{"code", "name"}
                )
        });
    }
```
- 对应的关联查询
```sql
select t1.id,
       t1.user_id userId, t2.name userName, t2.phone userPhone, t2.address userAddress,
       t1.state_code stateCode, t3.value stateName,
       t1.type_code typeCode, t4.value typeName
from t_order t1
left join t_user t2 on t2.id = t1.user_id
left join t_dict t3 on t3.code = t1.state_code and t3.type_code = 'order_state'
left join t_dict t4 on t4.code = t1.type_code and t3.type_code = 'order_type'
```

## 待优化
- 字典数据可以从缓存中获取
- 封装一些常用通用的规则
    - userId翻译userName
- 效率测试
    - 目前由于都是分页查询数据量很小，后期考虑进行一些极限数据量测试

## 相关依赖
- `mybatis-plus 3.4` ORM
- `mapstruct 1.4` 实体转换
- `swagger2 3.0` 接口文档，访问地址http://localhost:8080/swagger-ui

## 运行准备
- IDEA安装`lombok`插件
- 修改application.yml中数据库信息
- 执行/db/init.sql
