package com.ezreal.annotation;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
@Slf4j
public class RetryableAspect {

    @Pointcut("@annotation(Retryable)")
    private void doRetryable() {

    }

    /**
     * 环绕通知：redis 获取对应的缓存
     *
     * @param joinPoint 切点
     * @return 数据
     * @throws Throwable 错误
     */
    @SneakyThrows
    @Around("doRetryable()")
    public Object around(ProceedingJoinPoint joinPoint) {

        Method method = getCurrentMethod(joinPoint);
        Retryable retryable = method.getAnnotation(Retryable.class);

        //1. 最大次数判断
        int maxAttempts = retryable.maxAttempts();
        if (maxAttempts <= 1) {
            return joinPoint.proceed();
        }

        //2. 异常处理
        int times = 0;
        final Class<? extends Throwable> exceptionClass = retryable.value();
        while (times < maxAttempts) {
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                times++;

                // 超过最大重试次数 or 不属于当前处理异常
                if (times >= maxAttempts ||
                        !e.getClass().isAssignableFrom(exceptionClass)) {
                    throw new Throwable(e);
                }
            }
        }

        return null;
    }

    private Method getCurrentMethod(ProceedingJoinPoint point) {
        try {
            Signature sig = point.getSignature();
            MethodSignature msig = (MethodSignature) sig;
            Object target = point.getTarget();
            return target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


}
