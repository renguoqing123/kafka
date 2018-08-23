package com.kafka;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SynTest {
    
    private volatile int count = 0;  
    
    public ReentrantLock lock = new ReentrantLock();
    
    public synchronized void increment() {
        count++;
    }
    
    private int getCount() {
        return count;
    }
    
    /**
     * @throws InterruptedException
     */
    public void info() throws InterruptedException{
        SynTest s = new SynTest();
        int num = 5000;
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < num; i++) {
            Runnable r=new Runnable() {
                public void run() {
                    s.increment();
                };
            };
            executor.execute(r);
        }
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);//等待线程池终止
        System.out.println(s.getCount());
    }
    
    public static void main(String[] args) throws InterruptedException{
        new SynTest().info();
    }

}
