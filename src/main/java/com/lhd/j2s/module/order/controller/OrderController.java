package com.lhd.j2s.module.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhd.j2s.base.BasePageQuery;
import com.lhd.j2s.module.order.model.vo.OrderListVo;
import com.lhd.j2s.module.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lhd
 */
@Api(tags = "订单")
@RequestMapping("order")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "分页列表", response = OrderListVo.class)
    @GetMapping
    public IPage<OrderListVo> pageOrder(BasePageQuery query) {

        return orderService.pageOrder(query);
    }

    @ApiOperation(value = "详情", response = OrderListVo.class)
    @GetMapping("{id}")
    public OrderListVo getOrderById(@PathVariable("id") Long id) {

        return orderService.getOrderById(id);
    }
}
