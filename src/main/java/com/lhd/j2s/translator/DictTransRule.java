package com.lhd.j2s.translator;

public class DictTransRule extends FieldTransRule {

    private String typeCode;

    public DictTransRule(String typeCode, String keyFieldName) {
        this(typeCode, keyFieldName, keyFieldName.substring(0, keyFieldName.length() - 4) + "Name");
    }

    public DictTransRule(String typeCode, String keyFieldName, String valueFieldName) {
        super(keyFieldName, valueFieldName, null);
        this.typeCode = typeCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public static void main(String[] args) {
        System.out.println("stateCode".substring(0, "stateCode".length() - 4) + "Name");
    }
}
