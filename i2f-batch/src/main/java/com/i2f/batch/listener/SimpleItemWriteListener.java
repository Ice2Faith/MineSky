package com.i2f.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ltb
 * @date 2021/9/18
 */
@Component
public class SimpleItemWriteListener implements ItemWriteListener<Object> {
    private Logger logger=LoggerFactory.getLogger(SimpleItemWriteListener.class);
    private long startTime;
    @Override
    public void beforeWrite(List<?> list) {
        startTime=System.currentTimeMillis();
        logger.info("item write ...");
    }

    @Override
    public void afterWrite(List<?> list) {
        long useTime=System.currentTimeMillis()-startTime;
        logger.info("item write done,use time:"+useTime);
    }

    @Override
    public void onWriteError(Exception e, List<?> list) {
        logger.error("item write error:"+e.getMessage());
    }
}
