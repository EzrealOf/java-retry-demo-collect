package com.ezreal.service;

import com.ezreal.model.FileModel;
import com.ezreal.model.RetryModel;
import com.nurkiewicz.asyncretry.RetryExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * @author ezeal
 */
@Service
@Slf4j
public class RetryServiceImpl {

    @Resource
    private RetryExecutor executor;

    private static final Integer MAX_RETRY_TIMES = 2;

    /**
     * 重试
     * @param fileModelList 需要重试的队列
     */
    public void retry(List<FileModel> fileModelList) {
        fileModelList.forEach(fileModel -> {
            RetryModel<FileModel> retryModel = new RetryModel<>();
            retryModel.init(fileModel);
            Callable callable = getCallable(retryModel);
            if (Objects.nonNull(callable)) {
                CompletableFuture future = executor.getWithRetry(callable);

                future.thenAccept(reslut->{
                    log.info("获取返回值"+reslut.toString());
                });
            }

        });


    }

    /**
     *
     * @param retryModel 重试模型
     */
    public Callable getCallable(RetryModel<FileModel> retryModel){
        return () -> {
            log.info("开始第{}次上传文件，文件名称:{}以及文件路径为{}",retryModel.getRetryTimes(), retryModel.getT().getFileName(), retryModel.getT().getFileUrl());
            retryModel.retryTimesPlus();
            upload(retryModel);
            return retryModel;
        };
    }

    /**
     * 更新文件
     * @param retryModel 重试模型
     */
    private void upload(RetryModel retryModel){
        log.info("上传文件, 文件为:{}", retryModel.getT());
        throw new NullPointerException("null pointer exception");
    }
}
