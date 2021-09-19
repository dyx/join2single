package com.lhd.j2s.module.dict.service;

import java.util.List;
import java.util.Map;

/**
 * @author lhd
 */
public interface DictService {

    /**
     * 根据字典编码列表查询字典
     * @param typeCodeList
     * @return
     */
    Map<String, Map<Integer, String>> findDictMapByTypeCodeList(List<String> typeCodeList);
}
