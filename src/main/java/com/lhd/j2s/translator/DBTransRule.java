package com.lhd.j2s.translator;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 根据数据表信息翻译的规则
 * @param <M>
 * @author lhd
 */
public class DBTransRule<M extends BaseMapper> {

    /**
     * 要翻译的字段
     * 第一个字段为 keyFieldName
     */
    private String[] fieldNames;
    /**
     * 要调用的Mapper
     */
    private Class<M> mapperClass;
    /**
     * 要查询的列
     * 第一列为 where条件列
     */
    private String[] selectColumns;

    public DBTransRule(String[] fieldNames, Class<M> mapperClass, String[] selectColumns) {
        this.fieldNames = fieldNames;
        this.mapperClass = mapperClass;
        this.selectColumns = selectColumns;
    }

    public Class getMapperClass() {
        return mapperClass;
    }

    public void setMapperClass(Class<M> mapperClass) {
        this.mapperClass = mapperClass;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(String[] fieldNames) {
        this.fieldNames = fieldNames;
    }

    public String[] getSelectColumns() {
        return selectColumns;
    }

    public void setSelectColumns(String[] selectColumns) {
        this.selectColumns = selectColumns;
    }
}
