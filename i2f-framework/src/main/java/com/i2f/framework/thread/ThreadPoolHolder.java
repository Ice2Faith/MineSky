package com.i2f.framework.thread;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author ltb
 * @date 2021/9/6
 */
@Data
@NoArgsConstructor
public class ThreadPoolHolder {
    private ScheduledExecutorService schedulePool;
    private ExecutorService cachedPool;
}
