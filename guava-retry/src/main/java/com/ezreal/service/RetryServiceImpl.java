package com.ezreal.service;

import com.ezreal.util.RetryFactory;
import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        return (Callable<Boolean>) () -> {
            // do something useful here
            log.info("call...");
            throw new RuntimeException();
        };
    }


}
