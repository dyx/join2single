package com.lhd.j2s.module.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhd.j2s.base.BasePageQuery;
import com.lhd.j2s.module.order.model.vo.OrderListVo;

/**
 * @author lhd
 */
public interface OrderService {

    /**
     * 分页
     * @param query
     * @return
     */
    IPage<OrderListVo> pageOrder(BasePageQuery query);

    /**
     * 详情
     * @param id
     * @return
     */
    OrderListVo getOrderById(Long id);
}
