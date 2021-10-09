package com.hsms.mqclient.consumer.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ：河神
 * @date ：Created in 2021/7/11 2:51 下午
 */
public class ExecutorService {


   public static ThreadPoolExecutor threadPoolExecutor;

   static {
       threadPoolExecutor = new ThreadPoolExecutor(20, 100,
               30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5), new ThreadFactory() {
           @Override
           public Thread newThread(Runnable r) {
               return new Thread(r, "ConsumerExecutor");
           }
       });
   }

   public static ThreadPoolExecutor getExecutor(){
       return threadPoolExecutor;
   }

}
