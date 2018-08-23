package com.kafka;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
//        AtomicInteger atomicInteger = new AtomicInteger(0);
//        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//        scheduler.scheduleWithFixedDelay(() -> {
//            int i = atomicInteger.incrementAndGet();
//            System.out.println(i);
//            if (i >= 10) {
//                scheduler.shutdown();
//            }
//        }, 100L, 2000L, TimeUnit.MILLISECONDS);
        Stream<Integer> s=Stream.of(1,2,3,3);
        s.distinct().forEach(System.out::println);
        LinkedBlockingQueue<Runnable> link=new LinkedBlockingQueue<Runnable>(20);
        ThreadFactory th=new ThreadFactory() {
            
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
//                t.setDaemon(false);//守护线程
                System.out.println("创建线程"+t);
                return  t;
            }
        };
        ThreadPoolExecutor tp=new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, link,th);
        
        /**（1）  ThreadPoolExecutor.AbortPolicy 丢弃任务，并抛出 RejectedExecutionException 异常。
                                       （2）  ThreadPoolExecutor.CallerRunsPolicy：该任务被线程池拒绝，由调用 execute方法的线程执行该任务。
                                       （3）  ThreadPoolExecutor.DiscardOldestPolicy ： 抛弃队列最前面的任务，然后重新尝试执行任务。
                                       （4）  ThreadPoolExecutor.DiscardPolicy，丢弃任务，不过也不抛出异常。*/
        
        //抛出异常(默认值)
        tp.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        //直接丢弃任务
        tp.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        //丢弃old线程任务
        tp.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        //调用线程执行多余任务
        tp.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        
        for(int i=0;i<10;i++) {
            MyTask m=new MyTask();
            tp.execute(m);
            System.out.println("线程池中线程数目："+tp.getPoolSize()+"，队列中等待执行的任务数目："+
                    tp.getQueue().size()+"，已执行玩别的任务数目："+tp.getCompletedTaskCount());
        }
        
        tp.shutdown();
        
    }
}

class MyTask implements Runnable {

    @Override
    public void run() {
        System.out.println("正在执行run............");
        try {
            Thread.currentThread().sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行完成");
    }
    
}
  