package com.ensiewei.recordlife.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ensiewei.recordlife.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
