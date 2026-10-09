package com.atguigu.tingshu;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 * @author: atguigu
 * @create: 2026-06-10 11:15
 */
public class ThreadPoolTest {


    @Test
    public void testJUCUtils() {
        System.out.println("主线程:" + Thread.currentThread().getName());
        //1.创建固定核数数线程池，3个线程
        Executor executor = Executors.newFixedThreadPool(3);
        //2.让线程池执行"任务"
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            executor.execute(() -> {
                System.out.println("任务" + finalI + "执行了,线程:" + Thread.currentThread().getName());
            });
        }
    }

    /**
     * 创建线程池
     * 方式一：工具类Executors(禁止使用) 方式二：自定义线程池
     * 异步任务编排：CompletableFuture
     *
     * @param args
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1.创建线程池对象
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                3,
                5,
                10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),
                Executors.defaultThreadFactory(),
                //new ThreadPoolExecutor.AbortPolicy() //默认拒绝策略 抛出异常，任务拒绝
                //new ThreadPoolExecutor.DiscardPolicy()// 静默方式丢弃提交任务，不会抛出异常
                //new ThreadPoolExecutor.DiscardOldestPolicy()  //静默方式丢弃阻塞队列队首任务，并提交当前任务
                new ThreadPoolExecutor.CallerRunsPolicy() //返回给调用者线程执行 任务不会丢失
        );
        //2.提交任务到线程池
        //for (int i = 1; i <= 11; i++) {
        //    int finalI = i;
        //    executor.execute(()->{
        //        System.out.println("任务"+ finalI +"执行了,线程："+Thread.currentThread().getName());
        //    });
        //}

        //3.获取子线程执行结果
        //Future<String> future = executor.submit(() -> {
        //    System.out.println("任务执行了,线程：" + Thread.currentThread().getName());
        //    return "hello";
        //});
        //String s = future.get();
        //System.out.println(s);


        //4.1 创建异步任务D 不依赖其他异步任务，自己不需要返回结果
        CompletableFuture<Void> completableFutureD = CompletableFuture.runAsync(() -> {
            System.out.println("任务D执行了,线程：" + Thread.currentThread().getName());
        }, executor);
        //4.2 创建异步任务A 不依赖其他异步任务，需要结果给其他任务使用
        CompletableFuture<String> completableFutureA = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务A执行了,线程：" + Thread.currentThread().getName());
            return "HELLO";
        }, executor);

        //4.3 基于异步任务A，创建异步任务B 创建任务B依赖任务A的结果
        CompletableFuture<Void> completableFutureB = completableFutureA.thenAcceptAsync(a -> {
            System.out.println("任务B执行，获取到A结果：" + a);
        }, executor);
        //4.4 基于异步任务A，创建异步任务C 创建任务B依赖任务A的结果
        CompletableFuture<Void> completableFutureC = completableFutureA.thenAcceptAsync(a -> {
            System.out.println("任务C执行，获取到A结果：" + a);
        }, executor);

        //  汇总所有异步任务
        CompletableFuture.allOf(
                completableFutureA,
                completableFutureB,
                completableFutureC,
                completableFutureD
        ).join();

    }
}
