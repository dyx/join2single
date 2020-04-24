package com.lhd.j2s.module.dict.model.dataobj;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_dict")
public class DictDO {

    private String typeCode;

    private Integer code;

    private String value;
}
