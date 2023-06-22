package com.ensiewei.recordlife.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ensiewei.recordlife.entity.Event;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventMapper extends BaseMapper<Event> {

    @Delete("<script> " +
            "delete from event " +
            "where user_id = #{userId} and id in <foreach item='item' collection='eventIds' open='(' separator=',' close=')'>#{item}</foreach>; " +
            "</script>")
    void deleteByIds(@Param("userId") Integer userId, @Param("eventIds") List<Integer> eventIds);
}
