package com.feasttime.dishmap.utils;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 线程池
 */
public class SafeExecutor {
    private ThreadPoolExecutor mExeService;
    private static int DEFAULT_CORE_COUNT = 3;
    private static HashMap<String, ThreadPoolExecutor> mServiceMap = new HashMap<>();

    public SafeExecutor(String moduleName, int coreCount) {
        if (mServiceMap.containsKey(moduleName)) {
            mExeService = mServiceMap.get(moduleName);
        } else {
            mExeService = (ThreadPoolExecutor) Executors.newFixedThreadPool(coreCount);
            mServiceMap.put(moduleName, mExeService);
        }
    }

    @SuppressWarnings("unused")
    public SafeExecutor(String moduleName) {
        this(moduleName, DEFAULT_CORE_COUNT);
    }

    @SuppressWarnings("unused")
    public <Result> Result syncExecute(final Callable<Result> callable, long timeout) {
        Future<Result> future = mExeService.submit(callable);
        Result result = null;

        try {
            result = future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public void asyncExecute(Runnable runnable) {
        mExeService.execute(runnable);
    }

    @SuppressWarnings("unused")
    public void clearWaitRunnable() {
        if (mExeService != null) {
            BlockingQueue<Runnable> queue = mExeService.getQueue();
            queue.clear();
        }
    }
}
