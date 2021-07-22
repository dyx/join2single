package com.lhd.j2s.translator;

import cn.hutool.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 字段翻译器
 *
 * join2single
 * 连接查询转换单表查询
 *
 * 适合数量少（<=500条），一个主键翻译少量字段（<=3个）
 * 如果超过上述条件，建议使用连接查询
 *
 * @param <T>
 *
 * @author lhd
 */
@Slf4j
public class FieldTranslator<T> {

    /**
     * 要翻译的实体
     */
    private List<T> entityList;
    /**
     * 要翻译的实体类型
     */
    private Class<T> entityType;

    public FieldTranslator(List<T> entityList, Class<T> entityType) {
        this.entityList = entityList;
        this.entityType = entityType;
    }

    public List<T> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<T> entityList) {
        this.entityList = entityList;
    }

    public Class<T> getEntityType() {
        return entityType;
    }

    public void setEntityType(Class<T> entityType) {
        this.entityType = entityType;
    }

    /**
     * 根据给出的值翻译
     *
     * <p>例：</p>
     * <p><pre>new FieldTranslator<>(voList, OrderListVO.class)
     *  .trans(
     *          new FieldTransRule[]{
     *                  new FieldTransRule(
     *                          "createUserId",
     *                          new String[]{"createUserName"},
     *                          createUserIdValueMap
     *                  ),
     *                  new FieldTransRule(
     *                          "customCode",
     *                          new String[]{"customName", "customPhone", "customAddress"},
     *                          customCodeValueMap
     *                  )
     *          }
     *  );</pre></p>
     *
     * @param rules 字段规则数组
     */
    public void trans(FieldTransRule[] rules) {

        if (entityList == null
                || entityList.size() == 0
                || entityType == null
                || rules == null
                || rules.length == 0) {
            return;
        }

        for (T entity : entityList) {

            for (FieldTransRule rule : rules) {

                Field keyField = ReflectUtil.getField(entityType, rule.getKeyFieldName());
                keyField.setAccessible(true);
                for (String valueFieldName : rule.getValueFieldNames()) {

                    try {

                        Field valueField = ReflectUtil.getField(entityType, valueFieldName);
                        valueField.setAccessible(true);
                        valueField.set(entity,
                                rule.getValueMap()
                                        .get(keyField.get(entity).toString())
                                        .get(valueFieldName));

                    } catch (Exception e) {
                        if (log.isDebugEnabled()) {
                            log.debug("{}翻译异常：{}", valueFieldName, e.getStackTrace()[0]);
                        }
                    }
                }

            }
        }
    }

    /**
     * 根据给出数据表信息翻译
     *
     * <p>例1：根据主键字段，翻译一个值</p>
     *
     * <p><pre>new FieldTranslator<>(voList, UserListVO.class)
     *  .trans(
     *          new DBTransRule[]{
     *                  new DBTransRule<>(
     *                          new String[]{"createUserId", "createUserName"},
     *                          EmployeeMapper.class,
     *                          new String[]{"id", "name"}),
     *                  new DBTransRule<>(
     *                          new String[]{"updateUserId", "updateUserName"},
     *                          EmployeeMapper.class,
     *                          new String[]{"id", "name"})
     *          }
     *  );</pre></p>
     *
     *  <p>例2：根据主键字段，翻译多个值</p>
     *  <p><pre>new FieldTranslator<>(voList, OrderListVO.class)
     *  .trans(
     *          new DBTransRule[]{
     *                  new DBTransRule<>(
     *                          new String[]{"customCode", "customName", "customPhone", "customAddress"},
     *                          CustomMapper.class,
     *                          new String[]{"code", "name", "phone", "address"})
     *          }
     *  );</pre></p>
     *
     * @param dbRules 数据表规则数组
     */
    public void trans(DBTransRule[] dbRules) {

        if (entityList == null
                || entityList.size() == 0
                || entityType == null
                || dbRules == null
                || dbRules.length == 0) {
            return;
        }

        FieldTransRule[] rules = new FieldTransRule[dbRules.length];
         for (int i = 0; i < dbRules.length; i++) {

            try {
                FieldTransRule rule = TransUtils.build(entityList, entityType, dbRules[i].getMapperClass(), dbRules[i].getFieldNames(), dbRules[i].getSelectColumns());
                rules[i] = rule;
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("构建翻译规则异常：{}", e.getStackTrace()[0]);
                }
            }
        }

        trans(rules);
    }
}
