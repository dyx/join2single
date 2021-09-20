package com.lhd.j2s.trans.handler;

import com.lhd.j2s.trans.field.RefTransRule;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lhd
 */
@Slf4j
public class TransValueMapHandlerFactory {

    public static Map<String, Map<Object, Map<String, Object>>> getTransValueMap(List<RefTransRule> ruleList, Map<String, List<Object>> readFieldValueMap) {

        Map<String, Map<Object, Map<String, Object>>> transValueMap = new HashMap<>(16);
        if (ruleList == null || readFieldValueMap == null) {
            return transValueMap;
        }

        for (RefTransRule refTransRule : ruleList) {

            try {
                String key = refTransRule.getRefTransType().append(refTransRule.getKeyFieldName());
                List<Object> readFieldValueList = readFieldValueMap.get(key);
                String[] writeFieldNames = refTransRule.getValueFieldNames();
                if (readFieldValueList == null || readFieldValueList.size() == 0
                        || writeFieldNames == null || writeFieldNames.length == 0) {
                    continue;
                }
                Class<?> clazz = Class.forName(String.format("com.lhd.qd.trans.handler.%sTransValueMapHandler", refTransRule.getRefTransType().getType()));
                Method method = clazz.getMethod("getValueMap", List.class, String[].class);
                transValueMap.put(key, (Map) method.invoke(clazz.newInstance(), readFieldValueList, writeFieldNames));
            } catch (Exception e) {
                log.debug("获取翻译值出错，type={}, readFieldName={}, exception={}, cause={}", refTransRule.getRefTransType(), refTransRule.getKeyFieldName(), e.getClass(), e.getMessage());
            }
        }
        return transValueMap;
    }
}
