package com.lhd.j2s.module.order.model.dataobj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_order")
public class OrderDO {

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    private String userId;

    private Integer stateCode;

    private Integer typeCode;

    private String productCode;
}
