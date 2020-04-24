package com.lhd.j2s.module.order.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "订单列表返回结果")
@Data
public class OrderListVO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "用户手机号")
    private String userPhone;

    @ApiModelProperty(value = "用户地址")
    private String userAddress;

    @ApiModelProperty(value = "订单状态编码")
    private Integer stateCode;

    @ApiModelProperty(value = "订单状态名称")
    private String stateName;

    @ApiModelProperty(value = "订单类型编码")
    private Integer typeCode;

    @ApiModelProperty(value = "订单类型名称")
    private String typeName;

    @ApiModelProperty(value = "产品编码")
    private String productCode;

    @ApiModelProperty(value = "产品名称")
    private String productName;
}
