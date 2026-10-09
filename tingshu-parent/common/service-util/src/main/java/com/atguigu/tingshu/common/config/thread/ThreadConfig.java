package com.atguigu.tingshu.common.config.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.*;

/**
 * @author: atguigu
 * @create: 2026-06-10 13:54
 */
@Configuration
public class ThreadConfig {

    @Bean
    //@Primary
    public Executor threadPoolExecutor() {
        //1.创建线程池对象   IO密集型初始值参考=CPU核心数*2【+1】 应用类型分为两种：IO密集型、CPU密集型
        int cpuCount = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                cpuCount * 2,
                cpuCount * 2,
                10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(200),
                Executors.defaultThreadFactory(),
                //new ThreadPoolExecutor.AbortPolicy() //默认拒绝策略 抛出异常，任务拒绝
                //new ThreadPoolExecutor.DiscardPolicy()// 静默方式丢弃提交任务，不会抛出异常
                //new ThreadPoolExecutor.DiscardOldestPolicy()  //静默方式丢弃阻塞队列队首任务，并提交当前任务
                new ThreadPoolExecutor.CallerRunsPolicy() //返回给调用者线程执行 任务不会丢失
        );
        //2.提前创建核心线程
        executor.prestartCoreThread();
        return executor;
    }
}
