package com.ezreal.service;

import com.ezreal.util.RetryFactory;
import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.google.common.base.Predicates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author ezeal
 */
@Service
@Slf4j
public class RetryServiceImpl {

    @Resource
    private RetryFactory retryFactory;


    public void retry(){
        try {
            Retryer retryer = retryFactory.getRetryBuilder();
            retryer.call(getCallable());
        } catch (RetryException | ExecutionException e) {
            e.printStackTrace();
        }

    }
    private Callable getCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                // do something useful here
                log.info("call...");
                throw new RuntimeException();
            }
        };
    }


}
