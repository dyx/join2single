package com.lhd.j2s.trans.field;

/**
 * 字典字段翻译规则
 * @author lhd
 */
public class DictTransRule extends FieldTransRule {

    private String typeCode;

    public DictTransRule(String typeCode, String keyFieldName) {
        this(typeCode, keyFieldName, keyFieldName.substring(0, keyFieldName.length() - 4) + "Name");
    }

    public DictTransRule(String typeCode, String keyFieldName, String valueFieldName) {
        super(keyFieldName, valueFieldName);
        this.typeCode = typeCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
