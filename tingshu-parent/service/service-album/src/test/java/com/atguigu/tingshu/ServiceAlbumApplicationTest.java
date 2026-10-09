package com.atguigu.tingshu;

import com.atguigu.tingshu.common.rabbit.service.RabbitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServiceAlbumApplicationTest {

    @Autowired
    private RabbitService rabbitService;


    @Test
    public void testSendMessage() {
        rabbitService.sendMessage("exchange.test", "test1", "hello world");
    }

}
