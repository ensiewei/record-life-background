package com.ensiewei.recordlife.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ensiewei.recordlife.entity.User;
import com.ensiewei.recordlife.mapper.UserMapper;
import com.ensiewei.recordlife.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("register")
    public R register(@RequestBody User user) {
        User one = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (one != null) return R.error("用户名已存在");
        userMapper.insert(user);
        return R.ok();
    }

    @PostMapping("login")
    public R login(@RequestBody User user) {
        System.out.println(user);
        User one = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()).eq("password", user.getPassword()));
        if (one == null) return R.error("账号或密码错误");
        one.setToken(UUID.randomUUID().toString());
        userMapper.updateById(one);
        return R.ok().put("token", one.getToken());
    }

    @PostMapping("authenticate")
    public R authenticate(@RequestParam("token") String token) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("token", token));
        return user ==null ? R.error() : R.ok().put("user", user);
    }

    @PostMapping("update")
    public R update(@RequestBody User user) {
        userMapper.updateById(user);
        return R.ok();
    }
}
