package com.lhd.j2s.module.dict.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhd.j2s.module.dict.dao.DictMapper;
import com.lhd.j2s.module.dict.model.dataobj.DictDO;
import com.lhd.j2s.module.dict.service.DictService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, DictDO> implements DictService {

    @Override
    public Map<Integer, String> findDictMapByTypeCode(String typeCode) {

        List<DictDO> doList = list(Wrappers.<DictDO>lambdaQuery().eq(DictDO::getTypeCode, typeCode));

        Map<Integer, String> dictMap = new HashMap<>(16);
        for (DictDO dataObj : doList) {
            dictMap.put(dataObj.getCode(), dataObj.getValue());
        }
        return dictMap;
    }
}
