package com.ezreal.util;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.google.common.base.Predicates;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * @author ezeal
 */

@Data
@Component
public class RetryFactory {

    private Retryer retryer;

    public Retryer getRetryBuilder() {
        if (Objects.isNull(retryer)){
            synchronized (this){
                if (Objects.isNull(retryer)){
                    retryer = RetryerBuilder.<Boolean>newBuilder()
                            .retryIfResult(Predicates.isNull())
                            .retryIfExceptionOfType(IOException.class)
                            .retryIfRuntimeException()
                            .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                            .build();

                }
            }
        }
        return retryer;
    }
}
