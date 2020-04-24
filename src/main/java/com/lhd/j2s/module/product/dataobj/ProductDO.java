package com.lhd.j2s.module.product.dataobj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("t_product")
public class ProductDO {


    @TableId(value = "code", type = IdType.INPUT)
    private String code;

    private String name;

    private BigDecimal price;
}
