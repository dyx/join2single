package com.lhd.j2s.trans.field;

/**
 * 字段翻译规则
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

    public FieldTransRule(String keyFieldName, String valueFieldName) {
        this(keyFieldName, new String[]{valueFieldName});
    }

    public FieldTransRule(String keyFieldName, String[] valueFieldNames) {
        this.keyFieldName = keyFieldName;
        this.valueFieldNames = valueFieldNames;
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
}
