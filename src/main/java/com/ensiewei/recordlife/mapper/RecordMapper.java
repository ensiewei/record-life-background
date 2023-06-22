package com.ensiewei.recordlife.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ensiewei.recordlife.entity.Record;
import com.ensiewei.recordlife.vo.RecordVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RecordMapper extends BaseMapper<Record> {

    @Select("select t1.id, t2.name, t1.created from record t1 right join event t2 on t1.event_id = t2.id where t1.user_id = #{userId};")
    List<RecordVO> getRecord(@Param("userId") Integer userId);

    @Select("<script> " +
            "select t1.id, t2.name, t1.created " +
            "from record t1 right join event t2 on t1.event_id = t2.id " +
            "where t1.user_id = #{userId} and t2.id in <foreach item='item' collection='eventIds' open='(' separator=',' close=')'>#{item}</foreach>;" +
            "</script>")
    List<RecordVO> getRecord2(@Param("userId") Integer userId, @Param("eventIds") List<Integer> eventIds);

    @Delete("<script> " +
            "delete from record " +
            "where user_id = #{userId} and id in <foreach item='item' collection='recordIds' open='(' separator=',' close=')'>#{item}</foreach>;" +
            "</script>")
    void deleteByIds(@Param("userId") Integer userId, @Param("recordIds") List<Integer> recordIds);
}
