package com.lhd.j2s.module.order.model.dataobj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lhd
 */
@Data
@TableName("t_order")
public class OrderDo {

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    private String userId;

    private Integer status;

    private Integer type;

    private String productCode;
}
