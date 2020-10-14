package com.ezreal.service;

import com.ezreal.model.FileModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * @author ezeal
 */
@Service
@Slf4j
public class RetryServiceImpl {

    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 10000, multiplier = 2, maxDelay = 20000))
    public void upload(FileModel fileModel){

    }

    @Recover
    public void upload(Exception e, FileModel fileModel){
        log.error("重试多次后, 文件上传失败");
    }
}
