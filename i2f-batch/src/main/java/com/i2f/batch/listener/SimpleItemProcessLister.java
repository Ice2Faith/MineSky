package com.i2f.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

/**
 * @author ltb
 * @date 2021/9/18
 */
@Component
public class SimpleItemProcessLister implements ItemProcessListener<Object,Object> {
    private Logger logger=LoggerFactory.getLogger(SimpleItemProcessLister.class);
    private long startTime;
    @Override
    public void beforeProcess(Object o) {
        startTime=System.currentTimeMillis();
        logger.info("item process ...");
    }

    @Override
    public void afterProcess(Object o, Object o2) {
        long useTime=System.currentTimeMillis()-startTime;
        logger.info("item process done,use time:"+useTime);
    }

    @Override
    public void onProcessError(Object o, Exception e) {
        logger.error("item process error:"+e.getMessage());
    }
}
