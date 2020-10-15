package com.ezreal.controller;

import com.ezreal.model.FileModel;
import com.ezreal.service.RetryServiceImpl;
import com.ezreal.util.FileModelUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
        List<FileModel> fileModelList = FileModelUtil.init(10);
        fileModelList.forEach(fileModel -> {
            retryService.retry(fileModel);
        });
        return "success";
    }
}
