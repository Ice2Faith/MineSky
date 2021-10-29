package com.i2f.framework.thread;

import com.i2f.framework.thread.ThreadPoolHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author ltb
 * @date 2021/9/6
 */
@Configuration
public class ThreadPoolConfig {

    //默认大小32
    @Value("${thread.pool.schedule.core.size:32}")
    private int schedulePoolCoreSize;

    @Value("${thread.pool.cached.core.size:32}")
    private int cachedPoolCoreSize;

    @Bean
    public ScheduledExecutorService schedulePool(){
        ScheduledExecutorService service= Executors.newScheduledThreadPool(schedulePoolCoreSize);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                if(service!=null && !service.isShutdown()){
                    service.shutdown();
                }
            }
        }));
        return service;
    }

    @Bean
    public ExecutorService cachedPool(){
        ExecutorService exector= new ThreadPoolExecutor(cachedPoolCoreSize, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                if(exector!=null && !exector.isShutdown()){
                    exector.shutdown();
                }
            }
        }));
        return exector;
    }

    @Bean
    public ThreadPoolHolder poolHolder(){
        ThreadPoolHolder holder=new ThreadPoolHolder();
        holder.setCachedPool(cachedPool());
        holder.setSchedulePool(schedulePool());
        return holder;
    }
}
