package com.lhd.j2s.translator;

import java.util.Map;

/**
 * 字段翻译规则
 *
 * @author lhd
 */
public class FieldTransRule {

    /**
     * 主键字段
     * 根据主键值定位要翻译的值
     */
    private String keyFieldName;
    /**
     * 要翻译的字段名称
     */
    private String[] valueFieldNames;
    /**
     * 主键值和翻译值映射
     * <key, <valueFieldName, value>>
     */
    private Map<String, Map<String, Object>> valueMap;

    public FieldTransRule(String keyFieldName, String valueFieldName, Map<String, Map<String, Object>> valueMap) {
        this(keyFieldName, new String[]{valueFieldName}, valueMap);
    }

    public FieldTransRule(String keyFieldName, String[] valueFieldNames, Map<String, Map<String, Object>> valueMap) {
        this.keyFieldName = keyFieldName;
        this.valueFieldNames = valueFieldNames;
        this.valueMap = valueMap;
    }

    public String getKeyFieldName() {
        return keyFieldName;
    }

    public void setKeyFieldName(String keyFieldName) {
        this.keyFieldName = keyFieldName;
    }

    public String[] getValueFieldNames() {
        return valueFieldNames;
    }

    public void setValueFieldNames(String[] valueFieldNames) {
        this.valueFieldNames = valueFieldNames;
    }

    public Map<String, Map<String, Object>> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<String, Map<String, Object>> valueMap) {
        this.valueMap = valueMap;
    }
}
