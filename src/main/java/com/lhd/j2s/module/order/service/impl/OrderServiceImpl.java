package com.lhd.j2s.module.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhd.j2s.base.BasePageQuery;
import com.lhd.j2s.module.order.dao.OrderMapper;
import com.lhd.j2s.module.order.model.converter.AbstractOrderConverter;
import com.lhd.j2s.module.order.model.dataobj.OrderDO;
import com.lhd.j2s.module.order.model.vo.OrderListVO;
import com.lhd.j2s.module.order.service.OrderService;
import com.lhd.j2s.module.product.dao.ProductMapper;
import com.lhd.j2s.module.user.dao.UserMapper;
import com.lhd.j2s.translator.DBTransRule;
import com.lhd.j2s.translator.DictTransRule;
import com.lhd.j2s.translator.TransUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDO> implements OrderService {

    @Override
    public IPage<OrderListVO> pageOrder(BasePageQuery query) {

        LambdaQueryWrapper<OrderDO> queryWrapper = Wrappers.<OrderDO>lambdaQuery();

        IPage<OrderDO> doPage = page(new Page<>(query.getCurrent(), query.getSize()), queryWrapper);

        IPage<OrderListVO> voPage = AbstractOrderConverter.INSTANCE.doPage2ListVOPage(doPage);

        trans(voPage.getRecords(), OrderListVO.class);

        return voPage;
    }

    @Override
    public OrderListVO getOrderById(Long id) {

        OrderListVO listVO = AbstractOrderConverter.INSTANCE.do2ListVO(getById(id));

        trans(Collections.singletonList(listVO), OrderListVO.class);

        return listVO;
    }

    private <T> void trans(List<T> entityList, Class<T> entityType) {

        TransUtils.trans(entityList, entityType, new DictTransRule[]{
                new DictTransRule("order_state", "stateCode"),
                new DictTransRule("order_type", "typeCode", "typeName")
        });

        TransUtils.trans(entityList, entityType, new DBTransRule[]{
                new DBTransRule<>(
                        new String[]{"userId", "userName", "userPhone", "userAddress"},
                        UserMapper.class,
                        new String[]{"id", "name", "phone", "address"}
                ),
                new DBTransRule<>(
                        new String[]{"productCode", "productName"},
                        ProductMapper.class,
                        new String[]{"code", "name"}
                )
        });
    }
}
