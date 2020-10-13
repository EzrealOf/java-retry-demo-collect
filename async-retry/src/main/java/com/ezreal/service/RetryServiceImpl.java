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

    public void retry(List<FileModel> fileModelList) {
        fileModelList.forEach(fileModel -> {
            RetryModel<FileModel> retryModel = new RetryModel<>();
            retryModel.init(fileModel);
            Callable<Runnable> callable = getCallable(retryModel);
            if (Objects.nonNull(callable)) {
                CompletableFuture<Runnable> future = executor.getWithRetry(callable);

                future.thenAccept(reslut->{
                    log.info(reslut.toString());
                });
            }

        });


    }

    public Callable getCallable(RetryModel<FileModel> retryModel){
        if (Objects.isNull(retryModel) || retryModel.getRetryTimes() > MAX_RETRY_TIMES){
            return null;
        }
        return () -> {
            upload(retryModel);
            return retryModel;
        };
    }

    private void upload(RetryModel retryModel){

    }
}
