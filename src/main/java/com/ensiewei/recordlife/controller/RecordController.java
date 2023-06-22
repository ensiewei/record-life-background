package com.ensiewei.recordlife.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ensiewei.recordlife.entity.Event;
import com.ensiewei.recordlife.entity.Record;
import com.ensiewei.recordlife.entity.User;
import com.ensiewei.recordlife.mapper.EventMapper;
import com.ensiewei.recordlife.mapper.RecordMapper;
import com.ensiewei.recordlife.mapper.UserMapper;
import com.ensiewei.recordlife.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("record")
public class RecordController {

    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EventMapper eventMapper;

    @PostMapping("add")
    public R add(@RequestParam("token") String token, @RequestParam("eventId") Integer eventId) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("token", token));
        if (user == null) return R.error("非法令牌");
        Event event = eventMapper.selectById(eventId);
        if (event == null) return R.error("事件不存在");
        if (event.getUserId() != user.getId()) return R.error("该事件不属于你");
        Record record = new Record();
        record.setUserId(user.getId());
        record.setEventId(eventId);
        record.setCreated(System.currentTimeMillis());
        recordMapper.insert(record);
        return R.ok();
    }

    @PostMapping("delete")
    public R delete(@RequestParam("token") String token, @RequestBody List<Integer> recordIds) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("token", token));
        if (user == null) return R.error("非法令牌");
        recordMapper.deleteByIds(user.getId(), recordIds);
        return R.ok();
    }

    @PostMapping("update")
    public R update(@RequestBody Record record, @RequestParam("token") String token) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("token", token));
        if (user == null) return R.error("非法令牌");
        Record one = recordMapper.selectById(record.getId());
        if (one == null) return R.error("记录不存在");
        if (one.getUserId() != user.getId()) return R.error("该记录不属于你");
        recordMapper.updateById(record);
        return R.ok();
    }

    @PostMapping("all")
    public R all(@RequestParam("token") String token, @RequestBody(required = false) List<Integer> eventIds) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("token", token));
        if (user == null) return R.error("非法令牌");
        return R.ok().put("list", eventIds == null ? recordMapper.getRecord(user.getId()) : recordMapper.getRecord2(user.getId(), eventIds));
    }
}
