package com.lhd.j2s.module.order.model.converter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lhd.j2s.module.order.model.dataobj.OrderDo;
import com.lhd.j2s.module.order.model.vo.OrderListVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author lhd
 */
@Mapper
public abstract class AbstractOrderConverter {

    public static AbstractOrderConverter INSTANCE = Mappers.getMapper(AbstractOrderConverter.class);

    /**
     * doPage2ListVoPage
     * @param doPage
     * @return
     */
    public abstract Page<OrderListVo> doPage2ListVoPage(IPage<OrderDo> doPage);

    /**
     * do2ListVo
     * @param dataObj
     * @return
     */
    public abstract OrderListVo do2ListVo(OrderDo dataObj);
}
