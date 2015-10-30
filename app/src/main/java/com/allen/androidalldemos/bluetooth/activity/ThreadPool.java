package com.allen.androidalldemos.bluetooth.activity;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by allen on 2015/10/30.
 */
public class ThreadPool {

    private AtomicBoolean mStopped = new AtomicBoolean(Boolean.FALSE);
    private ThreadPoolExecutor mQueue;
    private final int coreSize = 2;
    private final int maxSize = 10;
    private final int timeOut = 2;
    private static ThreadPool pool = null;

    private ThreadPool() {
        mQueue = new ThreadPoolExecutor(coreSize, maxSize, timeOut, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), sThreadFactory);
        //    mQueue.allowCoreThreadTimeOut(true);
    }

    public static ThreadPool getInstance() {
        if (null == pool)
            pool = new ThreadPool();
        return pool;
    }

    public void excuteTask(Runnable run) {
        mQueue.execute(run);
    }

    public void closeThreadPool() {
        if (!mStopped.get()) {
            mQueue.shutdownNow();
            mStopped.set(Boolean.TRUE);
        }
    }

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "ThreadPool #" + mCount.getAndIncrement());
        }
    };

}
