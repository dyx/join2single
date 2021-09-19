package com.lhd.j2s.module.dict.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhd.j2s.module.dict.dao.DictMapper;
import com.lhd.j2s.module.dict.model.dataobj.DictDo;
import com.lhd.j2s.module.dict.service.DictService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lhd
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, DictDo> implements DictService {

    @Override
    public Map<String, Map<Integer, String>> findDictMapByTypeCodeList(List<String> typeCodeList) {

        // TODO 从缓存中获取

        List<DictDo> doList = list(Wrappers.<DictDo>lambdaQuery().in(DictDo::getTypeCode, typeCodeList));

        Map<String, Map<Integer, String>> resultMap = new HashMap<>(16);
        for (DictDo dataObj : doList) {
            Map<Integer, String> dictMap = resultMap.computeIfAbsent(dataObj.getTypeCode(), k -> new HashMap<>(16));
            dictMap.put(dataObj.getValue(), dataObj.getName());
        }

        return resultMap;
    }
}
