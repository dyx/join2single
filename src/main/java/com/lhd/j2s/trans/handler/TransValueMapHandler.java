package com.lhd.j2s.trans.handler;

import java.util.List;
import java.util.Map;

/**
 * @author lhd
 */
public interface TransValueMapHandler {

    /**
     * 获取翻译值Map
     * @param readFieldValueList
     * @param writeFieldNames
     * @return
     */
    Map<Object, Map<String, Object>> getValueMap(List<Object> readFieldValueList, String[] writeFieldNames);
}
