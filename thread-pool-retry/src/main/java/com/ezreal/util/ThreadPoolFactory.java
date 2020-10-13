package com.ezreal.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author ezeal
 */
@Component
public class ThreadPoolFactory {
    private ConcurrentHashMap<String, ThreadPoolExecutor> pool = new ConcurrentHashMap<>();

    private static ThreadPoolFactory factory = new ThreadPoolFactory();

    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private static final ThreadFactory THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("thread-call-runner-%d").build();

    private static final RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

    private static final BlockingQueue<Runnable> BLOCKING_QUEUE = new LinkedBlockingQueue<>(50);

    private ThreadPoolFactory() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                Set<Map.Entry<String, ThreadPoolExecutor>> set = pool.entrySet();
                for (Map.Entry<String, ThreadPoolExecutor> e : set) {
                    e.getValue().shutdownNow();
                }
                super.run();
            }
        });
    }

    public static ThreadPoolFactory getInstance() {
        return factory;
    }

    public ThreadPoolExecutor getThreadPool(String poolName) {
        ThreadPoolExecutor threadPool = pool.get(poolName);
        if (threadPool == null) {
            synchronized (pool) {
                if (pool.get(poolName) == null) {
                    threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, 10, 20L, TimeUnit.MINUTES, BLOCKING_QUEUE, THREAD_FACTORY, HANDLER);
                    pool.put(poolName, threadPool);
                }
            }
        }
        return threadPool;
    }

    public ThreadPoolExecutor getExistsThreadPool(String poolName) {
        ThreadPoolExecutor threadPool = pool.get(poolName);
        return threadPool;
    }

    public ConcurrentHashMap<String, ThreadPoolExecutor> getAllExistsThreadPool() {
        return pool;
    }

}