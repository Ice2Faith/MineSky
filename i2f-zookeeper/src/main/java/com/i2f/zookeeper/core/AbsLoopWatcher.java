package com.i2f.zookeeper.core;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ltb
 * @date 2021/9/22
 */
public abstract class AbsLoopWatcher implements Watcher {
    protected Logger logger=LoggerFactory.getLogger(this.getClass());

    @Override
    public final void process(WatchedEvent event) {
        boolean loop=onProcess(event);
        if(loop){
            try{
                getZooKeeper().exists(getPath(),this);
            }catch(Exception e){
                logger.warn("watcher {} next loop set failure.",getPath());
            }
        }
    }

    public abstract boolean onProcess(WatchedEvent event);

    public abstract ZooKeeper getZooKeeper();

    public abstract String getPath();
}
