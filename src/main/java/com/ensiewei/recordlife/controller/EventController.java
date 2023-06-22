package com.ensiewei.recordlife.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ensiewei.recordlife.entity.Event;
import com.ensiewei.recordlife.entity.User;
import com.ensiewei.recordlife.mapper.EventMapper;
import com.ensiewei.recordlife.mapper.UserMapper;
import com.ensiewei.recordlife.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("event")
public class EventController {

    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("add")
    public R add(@RequestParam("token") String token, @RequestParam("name") String name) {
        if (token.isEmpty() || name.isEmpty()) return R.error("非法参数");
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("token", token));
        if (user == null) return R.error();
        Event event = new Event();
        event.setName(name);
        event.setUserId(user.getId());
        eventMapper.insert(event);
        return R.ok();
    }

    @PostMapping("delete")
    public R delete(@RequestParam("token") String token, @RequestBody List<Integer> eventIds) {
        if (token.isEmpty()) return R.error("非法参数");
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("token", token));
        if (user == null) return R.error();
        eventMapper.deleteByIds(user.getId(), eventIds);
        return R.ok();
    }

    @PostMapping("update")
    public R update(@RequestBody Event event) {
        if (event == null) return R.error("非法参数");
        eventMapper.updateById(event);
        return R.ok();
    }

    @PostMapping("all")
    public R all(@RequestParam("token") String token) {
        if (token.isEmpty()) return R.error("非法参数");
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("token", token));
        if (user == null) return R.error();
        return R.ok().put("list", eventMapper.selectList(new QueryWrapper<Event>().eq("user_id", user.getId())));
    }
}
