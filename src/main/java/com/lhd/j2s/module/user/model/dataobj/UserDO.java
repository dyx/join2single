package com.lhd.j2s.module.user.model.dataobj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_user")
public class UserDO {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;
}
