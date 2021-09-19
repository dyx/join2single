package com.lhd.j2s.module.dict.model.dataobj;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lhd
 */
@Data
@TableName("t_dict")
public class DictDo {

    private String typeCode;

    private Integer value;

    private String name;
}
