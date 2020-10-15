package com.ezreal.controller;

import com.ezreal.model.FileModel;
import com.ezreal.service.RetryServiceImpl;
import com.google.common.collect.Lists;
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
        retryService.retry(init());
        return "success";
    }

    private List<FileModel> init() {
        List<FileModel> fileModelList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            FileModel fileModel = new FileModel();
            fileModel.setFileName("fileName_" + i);
            fileModel.setFileUrl("fileUrl" + i);
            fileModelList.add(fileModel);
        }
        return fileModelList;
    }
}
