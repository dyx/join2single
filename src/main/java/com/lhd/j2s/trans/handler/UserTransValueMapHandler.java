package com.lhd.j2s.trans.handler;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lhd.j2s.module.user.dao.UserMapper;
import com.lhd.j2s.module.user.model.dataobj.UserDo;
import com.lhd.j2s.trans.util.SpringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lhd
 */
public class UserTransValueMapHandler implements TransValueMapHandler {

    @Override
    public Map<Object, Map<String, Object>> getValueMap(List<Object> readFieldValueList, String[] writeFieldNames) {

        Map<Object, Map<String, Object>> allValueMap = new HashMap<>(16);
        List<UserDo> doList = SpringUtils.getBean(UserMapper.class).selectList(Wrappers.<UserDo>lambdaQuery().in(UserDo::getId, readFieldValueList));
        if (doList != null && doList.size() > 0) {
            for (UserDo dataObj : doList) {
                Map<String, Object> valueMap = new HashMap<>(8);
                for (int i = 0; i < writeFieldNames.length; i++) {
                    String key = writeFieldNames[i];
                    if (key != null && key.length() > 0) {
                        if (i == 0) {
                            valueMap.put(key, dataObj.getName());
                        }
                        else if (i == 1) {
                            valueMap.put(key, dataObj.getPhone());
                        }
                        else if (i == 2) {
                            valueMap.put(key, dataObj.getAddress());
                        }
                    }
                }
                allValueMap.put(dataObj.getId(), valueMap);
            }
        }
        return allValueMap;
    }
}
