package com.lhd.j2s.trans.field;

import com.lhd.j2s.trans.consts.RefTransType;

/**
 * 参照字段翻译规则
 * @author lhd
 */
public class RefTransRule extends FieldTransRule {

    private RefTransType refTransType;

    public RefTransRule(RefTransType refTransType, String keyFieldName, String[] valueFieldNames) {
        super(keyFieldName, valueFieldNames);
        this.refTransType = refTransType;
    }

    public RefTransType getRefTransType() {
        return refTransType;
    }

    public void setRefTransType(RefTransType refTransType) {
        this.refTransType = refTransType;
    }
}
