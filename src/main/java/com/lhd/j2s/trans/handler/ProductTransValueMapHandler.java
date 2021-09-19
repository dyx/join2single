package com.lhd.j2s.trans.handler;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lhd.j2s.module.product.dao.ProductMapper;
import com.lhd.j2s.module.product.dataobj.ProductDo;
import com.lhd.j2s.trans.util.SpringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lhd
 */
public class ProductTransValueMapHandler implements TransValueMapHandler {

    @Override
    public Map<Object, Map<String, Object>> getValueMap(List<Object> readFieldValueList, String[] writeFieldNames) {

        Map<Object, Map<String, Object>> allValueMap = new HashMap<>(16);
        List<ProductDo> doList = SpringUtils.getBean(ProductMapper.class).selectList(Wrappers.<ProductDo>lambdaQuery().in(ProductDo::getCode, readFieldValueList));
        if (doList != null && doList.size() > 0) {
            for (ProductDo dataObj : doList) {
                Map<String, Object> valueMap = new HashMap<>(8);
                for (int i = 0; i < writeFieldNames.length; i++) {
                    String key = writeFieldNames[i];
                    if (key != null && key.length() > 0) {
                        if (i == 0) {
                            valueMap.put(key, dataObj.getName());
                        }
                    }
                }
                allValueMap.put(dataObj.getCode(), valueMap);
            }
        }
        return allValueMap;
    }
}
