package com.atguigu.tingshu;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: atguigu
 * @create: 2026-06-12 14:28
 */
@Slf4j
@SpringBootTest
public class LogTest {

    @Test
    public void logTest() {
        try {
            log.debug("debug...");
            log.info("info...");
            log.warn("warn...");
            int i = 1 / 0;
        } catch (Exception e) {
            log.error("error...错误级别日志", e);
            throw new RuntimeException(e);
        }
    }
}
