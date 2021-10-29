package com.i2f.zookeeper.config;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

/**
 * @author ltb
 * @date 2021/9/22
 */
@Configuration
public class ZooKeeperConfig {
    private Logger logger = LoggerFactory.getLogger(ZooKeeperConfig.class);

    @Value("${zookeeper.address:127.0.0.1:2181}")
    private String address;

    @Value("${zookeeper.timeout:40000}")
    private int timeout;

    @Bean("zooKeeper")
    public ZooKeeper getZookeeper(){
        ZooKeeper keeper=null;

        try{
            final CountDownLatch latch=new CountDownLatch(1);

            keeper=new ZooKeeper(address, timeout, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if(Event.KeeperState.SyncConnected==event.getState()){
                        latch.countDown();
                    }
                }
            });
            latch.await();
            logger.info("zookeeper connected:"+address);
        }catch(Exception e){
            e.printStackTrace();
        }
        return keeper;
    }

}
