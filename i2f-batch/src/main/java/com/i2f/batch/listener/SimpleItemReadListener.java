package com.i2f.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

/**
 * @author ltb
 * @date 2021/9/18
 */
@Component
public class SimpleItemReadListener implements ItemReadListener<Object> {
    private Logger logger = LoggerFactory.getLogger(SimpleItemReadListener.class);
    private long startTime;
    @Override
    public void beforeRead() {
        startTime=System.currentTimeMillis();
        logger.info("item read ...");
    }

    @Override
    public void afterRead(Object o) {
        long useTime=System.currentTimeMillis()-startTime;
        logger.info("item read done,use time:"+useTime);
    }

    @Override
    public void onReadError(Exception e) {
        logger.error("item read error:"+e.getMessage());
    }
}
