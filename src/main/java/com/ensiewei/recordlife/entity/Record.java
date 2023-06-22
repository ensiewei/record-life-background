package com.ensiewei.recordlife.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Record {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer eventId;
    private Long created;
}
