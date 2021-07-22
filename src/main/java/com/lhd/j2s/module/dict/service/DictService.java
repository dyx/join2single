package com.lhd.j2s.module.dict.service;

import java.util.Map;

public interface DictService {
    /**
     * 根据字典编码获取字典Map，用于翻译
     * @param typeCode
     * @return
     */
    Map<Integer, String> findDictMapByTypeCode(String typeCode);
}
