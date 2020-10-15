package com.ezreal.controller;

import com.ezreal.service.RetryServiceImpl;
import com.ezreal.util.FileModelUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ezeal
 */
@RestController
public class HelloController {

    @Resource
    private RetryServiceImpl retryService;

    @GetMapping("/hello")
    public String helloIndex() {
        return "hello";
    }

    @GetMapping("/testRetry")
    public String testRetry() {
        retryService.retry(FileModelUtil.init(10));
        return "success";
    }


}
