package com.lhd.j2s.module.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhd.j2s.base.BasePageQuery;
import com.lhd.j2s.module.order.dao.OrderMapper;
import com.lhd.j2s.module.order.model.converter.AbstractOrderConverter;
import com.lhd.j2s.module.order.model.dataobj.OrderDo;
import com.lhd.j2s.module.order.model.vo.OrderListVo;
import com.lhd.j2s.module.order.service.OrderService;
import com.lhd.j2s.trans.annotation.DictTrans;
import com.lhd.j2s.trans.annotation.DictTranslating;
import com.lhd.j2s.trans.annotation.RefTrans;
import com.lhd.j2s.trans.annotation.RefTranslating;
import com.lhd.j2s.trans.consts.RefTransType;
import org.springframework.stereotype.Service;

/**
 * @author lhd
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDo> implements OrderService {

    @DictTranslating({
            @DictTrans(typeCode = "order_status", readFieldName = "status"),
            @DictTrans(typeCode = "order_type", readFieldName = "type")
    })
    @RefTranslating({
            @RefTrans(type = RefTransType.USER, readFieldName = "userId", writeFieldNames = {"userName", "userPhone", "userAddress", "test"}),
            @RefTrans(type = RefTransType.PRODUCT, readFieldName = "productCode", writeFieldNames = "productName")
    })
    @Override
    public IPage<OrderListVo> pageOrder(BasePageQuery query) {

        IPage<OrderDo> doPage = page(new Page<>(query.getCurrent(), query.getSize()), Wrappers.emptyWrapper());

        return AbstractOrderConverter.INSTANCE.doPage2ListVoPage(doPage);
    }

    @DictTranslating({
            @DictTrans(typeCode = "order_status", readFieldName = "status"),
            @DictTrans(typeCode = "order_type", readFieldName = "type")
    })
    @RefTranslating({
            // 翻译时会按顺序匹配值（UserTransValueMapHandler），此处writeFieldNames顺序需要和处理器中一致
            @RefTrans(type = RefTransType.USER, readFieldName = "userId", writeFieldNames = {"userName", "", "userAddress"}),
            @RefTrans(type = RefTransType.PRODUCT, readFieldName = "productCode", writeFieldNames = "productName")
    })
    @Override
    public OrderListVo getOrderById(Long id) {
        return AbstractOrderConverter.INSTANCE.do2ListVo(getById(id));
    }
}
