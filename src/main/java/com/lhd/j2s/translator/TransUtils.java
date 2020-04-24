package com.lhd.j2s.translator;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lhd.j2s.module.dict.dao.DictMapper;
import com.lhd.j2s.module.dict.model.dataobj.DictDO;
import com.lhd.j2s.util.SpringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 字段翻译工具类
 *
 * @author lhd
 */
public class TransUtils {

    /**
     * 根据给出的值翻译
     *
     * @param entityList
     * @param entityType
     * @param rules
     * @param <T>
     */
    public static <T> void trans(List<T> entityList, Class<T> entityType, FieldTransRule[] rules) {
        new FieldTranslator<>(entityList, entityType).trans(rules);
    }

    /**
     * 根据给出数据表信息翻译
     *
     * @param entityList
     * @param entityType
     * @param rules
     * @param <T>
     */
    public static <T> void trans(List<T> entityList, Class<T> entityType, DBTransRule[] rules) {
        new FieldTranslator<>(entityList, entityType).trans(rules);
    }

    /**
     * 根据给的字典翻译
     * @param entityList
     * @param entityType
     * @param rules
     * @param <T>
     */
    public static <T> void trans(List<T> entityList, Class<T> entityType, DictTransRule[] rules) {

        for (DictTransRule rule : rules) {
            LambdaQueryWrapper<DictDO> queryWrapper = Wrappers.<DictDO>lambdaQuery()
                    .select()
                    .eq(DictDO::getTypeCode, rule.getTypeCode());
            List<DictDO> doList = SpringUtils.getBean(DictMapper.class).selectList(queryWrapper);
            Map<String, Map<String, Object>> valueMap = new HashMap<>();
            for (DictDO dataObj : doList) {
                valueMap.put(dataObj.getCode().toString(), Collections.singletonMap(rule.getValueFieldNames()[0], dataObj.getValue()));
            }
            rule.setValueMap(valueMap);
        }

        new FieldTranslator<>(entityList, entityType).trans(rules);
    }

    /**
     * 构造字段翻译规则
     * 查询数据表信息后组装
     *
     * @param entityList
     * @param entityType
     * @param mapperClass
     * @param fieldNames
     * @param selectColumns
     * @param <T>
     * @return
     */
    public static <T> FieldTransRule build(List<T> entityList, Class<T> entityType, Class<?> mapperClass, String[] fieldNames, String[] selectColumns) {

        String keyFieldName = fieldNames[0];
        String whereIdColumn = selectColumns[0];
        List<Map<String, Object>> valueList = null;
        try {

            QueryWrapper queryWrapper = Wrappers.query()
                    .select(appendColumnAlias(selectColumns, fieldNames))
                    .in(whereIdColumn, getWhereIdList(entityList, entityType, keyFieldName));

            valueList = (List<Map<String, Object>>) ((BaseMapper) SpringUtils.getBean(mapperClass)).selectMaps(queryWrapper);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return build(keyFieldName, Arrays.copyOfRange(fieldNames, 1, fieldNames.length), valueList);
    }

    /**
     * 构造字段翻译规则
     * 将要翻译的值由list转换为map
     *
     * @param keyFieldName
     * @param valueFieldNames
     * @param valueList
     * @return
     */
    public static FieldTransRule build(String keyFieldName, String[] valueFieldNames, List<Map<String, Object>> valueList) {

        Map<String, Map<String, Object>> resultMap = new HashMap<>();
        for (Map<String, Object> valueMap : valueList) {
            try {
                Map<String, Object> map = new HashMap<>();
                for (String valueFieldName : valueFieldNames) {
                    map.put(valueFieldName, valueMap.get(valueFieldName));
                }
                resultMap.put(valueMap.get(keyFieldName).toString(), map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new FieldTransRule(keyFieldName, valueFieldNames, resultMap);
    }

    /**
     * 追加列别名
     * @param selectColumns
     * @param fieldNames
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String[] appendColumnAlias(String[] selectColumns, String[] fieldNames) {
        String[] resultSelectColumns = new String[selectColumns.length];
        for (int i = 0; i < selectColumns.length; i++) {
            resultSelectColumns[i] = String.format("%s %s", selectColumns[i], fieldNames[i]);
        }
        return resultSelectColumns;
    }

    /**
     * 获取要翻译值的主键
     * @param entityList
     * @param entityType
     * @param keyFieldName
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List getWhereIdList(List<T> entityList, Class<T> entityType, String keyFieldName) {
        List whereIdList = new ArrayList();
        for (T entity : entityList) {
            try {
                Field keyField = FieldUtils.getField(entityType, keyFieldName, true);
                whereIdList.add(keyField.get(entity));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return whereIdList;
    }
}
