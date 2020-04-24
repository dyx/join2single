package com.lhd.j2s.module.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhd.j2s.base.BasePageQuery;
import com.lhd.j2s.module.order.model.vo.OrderListVO;

public interface OrderService {

    IPage<OrderListVO> pageOrder(BasePageQuery query);

    OrderListVO getOrderById(Long id);
}
