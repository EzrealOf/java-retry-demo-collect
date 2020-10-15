package com.ezreal.service;

import com.ezreal.model.FileModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ezeal
 */
@Service
@Slf4j
public class RetryServiceImpl {

    @Resource
    private FileServiceImpl fileService;


    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 10000, multiplier = 2, maxDelay = 20000))
    public void retry(FileModel fileModel){
        fileService.upload(fileModel);
    }

    @Recover
    public void retry(Exception e, FileModel fileModel){
        log.error("文件名称: {}, 地址: {}, 上传失败, 错误原因", fileModel.getFileName(), fileModel.getFileUrl(), e);
    }
}
