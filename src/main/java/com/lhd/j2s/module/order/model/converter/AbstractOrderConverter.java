package com.lhd.j2s.module.order.model.converter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lhd.j2s.module.order.model.dataobj.OrderDO;
import com.lhd.j2s.module.order.model.vo.OrderListVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public abstract class AbstractOrderConverter {

    public static AbstractOrderConverter INSTANCE = Mappers.getMapper(AbstractOrderConverter.class);

    public abstract Page<OrderListVO> doPage2ListVOPage(IPage<OrderDO> doPage);

    public abstract OrderListVO do2ListVO(OrderDO dataObj);
}
