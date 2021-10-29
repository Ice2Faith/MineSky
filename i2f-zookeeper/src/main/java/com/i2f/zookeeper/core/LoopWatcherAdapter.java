package com.i2f.zookeeper.core;

import lombok.Data;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ltb
 * @date 2021/9/22
 */
@Data
public class LoopWatcherAdapter extends AbsLoopWatcher {
    protected Logger logger=LoggerFactory.getLogger(this.getClass());
    protected ZooKeeper keeper;
    protected String path;
    protected IWatchProcessor processor;

    public LoopWatcherAdapter(ZooKeeper keeper,String path){
        this.keeper=keeper;
        this.path=path;
    }

    public LoopWatcherAdapter(ZooKeeper keeper, String path, IWatchProcessor processor) {
        this.keeper = keeper;
        this.path = path;
        this.processor = processor;
    }

    @Override
    public boolean onProcess(WatchedEvent event) {
        if(processor!=null){
            this.logger=LoggerFactory.getLogger(processor.getClass());
            return processor.process(event,this);
        }
        return true;
    }

    @Override
    public ZooKeeper getZooKeeper() {
        return keeper;
    }

    @Override
    public String getPath() {
        return path;
    }
}
