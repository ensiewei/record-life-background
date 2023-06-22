package com.ensiewei.recordlife;

import com.ensiewei.recordlife.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class RecordLifeApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        for (int i = 0; i < 10; i++) {
            System.out.println(UUID.randomUUID().toString().length());
        }

    }

}
