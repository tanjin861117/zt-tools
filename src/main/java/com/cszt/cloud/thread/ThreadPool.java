package com.cszt.cloud.thread;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程池
 * @ClassName: ThreadPool   
 * @Description: 线程池  
 * @author: tanjin  
 * @date:2018年9月26日 下午2:58:23
 */
public class ThreadPool {
    /**
     * 固定大小线程池标识
     */
    private static final String THREAD_POOL_FIX = "fix";
    /**
     * 不固定大小线程池标识
     */
    private static final String THREAD_POOL_CACHE = "cache";
    /**
     * 单work线程池标识
     */
    private static final String THREAD_POOL_SINGLE = "single";
    // 默认线程数
    private static final int DEFUAL_THREADNUM = 20;
    private static Logger logger = LoggerFactory.getLogger(ThreadPool.class);
    private static volatile Set<ThreadPool> poolSet = new HashSet<ThreadPool>();
    protected boolean run = true;
    // 线程数
    private int threadNum = DEFUAL_THREADNUM;
    private ExecutorService service;
    private boolean init = false;
    private String type;

    /**
     * <pre>
     * 参数type取值不同创建不同的线程池
     * 1.	THREAD_POOL_CACHE 使用的ExecutorService.newCachedThreadPool
     * 2.	THREAD_POOL_FIX	  使用的ExecutorService.newFixedThreadPool
     * 3.	THREAD_POOL_SINGLE使用的ExecutorService.newSingleThreadExecutor
     * 线程池中线程个数为： 20
     * </pre>
     *
     * @param type 只支持{@link ISpecialStr}
     *             中的THREAD_POOL_CACHE，THREAD_POOL_FIX，THREAD_POOL_SINGLE
     */
    public ThreadPool(String type) {
        this(type, DEFUAL_THREADNUM);
    }

    /**
     * <pre>
     * 默认使用ExecutorService.newFixedThreadPool线程池
     * </pre>
     *
     * @param threadNum 线程池中线程个数
     */
    public ThreadPool(int threadNum) {
        this(THREAD_POOL_FIX, threadNum);
    }

    /**
     * <pre>
     * 通过对应参数创建线程池
     * 参数type取值不同创建不同的线程池
     * 1.	THREAD_POOL_CACHE 使用的ExecutorService.newCachedThreadPool
     * 2.	THREAD_POOL_FIX	  使用的ExecutorService.newFixedThreadPool
     * 3.	THREAD_POOL_SINGLE使用的ExecutorService.newSingleThreadExecutor
     * </pre>
     *
     * @param type      只支持{@link ISpecialStr}
     *                  中的THREAD_POOL_CACHE，THREAD_POOL_FIX，THREAD_POOL_SINGLE
     * @param threadNum 线程池中线程个数
     */
    public ThreadPool(String type, int threadNum) {
        this.threadNum = threadNum;
        this.type = type;
        check();
        addService();
        init = true;
    }

    protected static int getDefualThreadnum() {
        return DEFUAL_THREADNUM;
    }

    public static void stopService() {
        if (poolSet.isEmpty()) {
            logger.warn(" No is service run! ");
        } else {
            for (ThreadPool pool : poolSet) {
                if (pool.init) {
                    logger.info(" Stop service " + pool.getClass());
                    pool.stopOne();
                }
            }
        }
    }

    private void check() {
        if (THREAD_POOL_CACHE.equals(type)) {
            service = Executors.newCachedThreadPool();
        } else if (THREAD_POOL_FIX.equals(type)) {
            if (threadNum <= 0) {
                threadNum = DEFUAL_THREADNUM;
            }
            // 初始化线程池
            service = Executors.newFixedThreadPool(threadNum);
        } else if (THREAD_POOL_SINGLE.equals(type)) {
            service = Executors.newSingleThreadExecutor();
        } else {
            throw new RuntimeException("Thread type is not support, Only support cache,fix,single");
        }
    }

    /**
     * <pre>
     * 添加任务，可以调用多次。
     * </pre>
     *
     * @param task
     */
    public void addTask(Runnable task) {
        service.execute(task);
    }

    private void stopOne() {
        try {
            run = false;
            service.shutdown();
        } catch (Exception e) {
            logger.error("stop service error", e.getMessage());
        }
    }

    private void addService() {
        poolSet.add(this);
    }

}
