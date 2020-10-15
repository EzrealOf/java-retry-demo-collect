package com.ezreal.controller;

import com.ezreal.service.RetryServiceImpl;
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
    public String HelloIndex() {
        return "hello";
    }

    @GetMapping("/retryTest")
    public String retryTest(){

        retryService.retry();
        return "success";
    }
}
