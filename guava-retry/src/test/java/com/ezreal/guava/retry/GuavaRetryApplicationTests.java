package com.ezreal.guava.retry;

import com.ezreal.service.RetryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class GuavaRetryApplicationTests {


    @Resource
    private RetryServiceImpl retryService;

    @Test
    public void test() {
        retryService.retry();
    }

}
