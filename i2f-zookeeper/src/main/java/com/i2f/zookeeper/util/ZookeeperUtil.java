package com.i2f.zookeeper.util;

import com.i2f.zookeeper.core.IWatchProcessor;
import com.i2f.zookeeper.core.LoopWatcherAdapter;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ltb
 * @date 2021/9/22
 */
@Component
public class ZookeeperUtil {
    private static volatile String CHAR_SET="UTF-8";

    private Logger logger= LoggerFactory.getLogger(ZookeeperUtil.class);

    @Autowired
    private ZooKeeper keeper;

    public ZooKeeper getKeeper(){
        return keeper;
    }

    public Stat exists(String path,boolean useDefaultWatcher){
        try{
            return keeper.exists(path,useDefaultWatcher);
        }catch(Exception e){
            logger.error("node exists check error:path={},exception={}",path,e);
        }
        return null;
    }

    public Stat exists(String path,Watcher watcher){
        try{
            return keeper.exists(path,watcher);
        }catch(Exception e){
            logger.error("node exists check error:path={},exception={}",path,e);
        }
        return null;
    }

    public boolean watch(String path,Watcher watcher){
        try{
            Stat stat=keeper.exists(path, watcher);
            return true;
        }catch(Exception e){
            logger.error("node exists check error:path={},exception={}",path,e);
        }
        return false;
    }

    public boolean watchForever(String path, IWatchProcessor processor){
        LoopWatcherAdapter adapter=new LoopWatcherAdapter(keeper,path,processor);
        return watch(path,adapter);
    }

    public boolean createNode(String path,String data){
        try{
            byte[] bytes=data.getBytes(CHAR_SET);
            keeper.create(path,bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            return true;
        }catch(Exception e){
            logger.error("node create error:path={},exception={}",path,e);
        }
        return false;
    }

    public boolean updateNode(String path,String data){
        try{
            byte[] bytes=data.getBytes(CHAR_SET);
            keeper.setData(path,bytes,-1);
            return true;
        }catch(Exception e){
            logger.error("node update error:path={},exception={}",path,e);
        }
        return false;
    }

    public boolean deleteNode(String path){
        try{
            keeper.delete(path,-1);
            return true;
        }catch(Exception e){
            logger.error("node delete error:path={},exception={}",path,e);
        }
        return false;
    }

    public List<String> getChildren(String path){
        List<String> list=new ArrayList<>();
        try{
            list=keeper.getChildren(path,false);
        }catch(Exception e){
            logger.error("node children error:path={},exception={}",path,e);
        }
        return list;
    }

    public String getData(String path, Watcher watcher){
        try{
            Stat stat=new Stat();
            byte[] bytes=keeper.getData(path,watcher,stat);
            return new String(bytes, CHAR_SET);
        }catch(Exception e){
            logger.error("node get data error:path={},exception={}",path,e);
        }
        return null;
    }
}
