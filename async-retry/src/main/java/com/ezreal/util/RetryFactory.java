package com.ezreal.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.nurkiewicz.asyncretry.AsyncRetryExecutor;
import com.nurkiewicz.asyncretry.RetryExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import java.util.concurrent.*;


@Configuration
public class RetryFactory {

    private RetryExecutor executor;

    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private static final ThreadFactory THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("thread-call-runner-%d").build();

    private static final RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

    public void setExecutor(RetryExecutor executor) {
        this.executor = executor;
    }

    @Bean
    public RetryExecutor getExecutor() {
        if (Objects.isNull(executor)) {
            return init();
        }
        return executor;
    }

    public RetryExecutor init() {
        ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(CORE_POOL_SIZE, THREAD_FACTORY, HANDLER);
        RetryExecutor executor = new AsyncRetryExecutor(scheduler).
                retryOn(Exception.class).
                //500ms times 2 after each retry
                withExponentialBackoff(5000, 2).
                //10 seconds
                withMaxDelay(5_000).
                //add between +/- 100 ms randomly
                withUniformJitter().
                withMaxRetries(2);
        return executor;
    }
}
