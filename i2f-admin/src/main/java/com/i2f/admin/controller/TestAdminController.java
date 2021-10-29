package com.i2f.admin.controller;

import com.i2f.admin.feign.IUserController;
import com.i2f.common.data.RespData;
import com.i2f.common.util.SecurityUtils;
import com.i2f.common.util.ServletUtils;
import com.i2f.common.util.TestBean;
import com.i2f.elasticsearch.util.EsQryUtil;
import com.i2f.framework.advice.annotation.SecureParams;
import com.i2f.mq.rabbit.config.defaults.direct.impl.DefaultDirectRabbitMqSender;
import com.i2f.service.IAdminTestService;
import com.i2f.service.user.model.SysUser;
import com.i2f.zookeeper.core.AbsLoopWatcher;
import com.i2f.zookeeper.core.IWatchProcessor;
import com.i2f.zookeeper.core.LoopWatcherAdapter;
import com.i2f.zookeeper.util.ZookeeperUtil;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooKeeper;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ltb
 * @date 2021/8/30
 */
@RestController
@RequestMapping("admin/test")
public class TestAdminController {

    @Autowired
    private IAdminTestService adminTestService;

    @Autowired
    private IUserController userController;

    @Autowired
    private DefaultDirectRabbitMqSender mqDirectSender;

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ZookeeperUtil zookeeperUtil;

    @RequestMapping("zoo/add")
    public RespData zooAdd(String data){
        if(data==null){
            data=System.currentTimeMillis()+"";
        }
        boolean success=zookeeperUtil.createNode("/test/zoo",data);
        zookeeperUtil.watchForever("/test/zoo", new IWatchProcessor() {
            @Override
            public boolean process(WatchedEvent event, LoopWatcherAdapter adapter) {
                adapter.getLogger().info("Loop:【Watcher监听事件】={}",event.getState());
                adapter.getLogger().info("Loop:【监听路径为】={}",event.getPath());
                adapter.getLogger().info("Loop:【监听的类型为】={}",event.getType()); //  三种监听类型： 创建，删除，更新
                return true;
            }
        });
        return RespData.success(success);
    }

    @RequestMapping("zoo/upd")
    public RespData zooUpd(String data){
        if(data==null){
            data=System.currentTimeMillis()+"";
        }
        boolean success=zookeeperUtil.updateNode("/test/zoo",data);
        return RespData.success(success);
    }

    @RequestMapping("zoo/get")
    public RespData zooGet(){
        String data=zookeeperUtil.getData("/test/zoo",new TestZooWatcher());
        return RespData.success(data).add("path","/test/zoo");
    }

    @RequestMapping("zoo/del")
    public RespData zooDel(){
        boolean success=zookeeperUtil.deleteNode("/test/zoo");
        return RespData.success(success);
    }

    @RequestMapping("es/qry")
    public Object esQry() throws IOException {
        SearchHits hits= EsQryUtil.builder()
                .likes("name","zhang")
                .should()
                .eqs("id","001")
                .doQry()
                .request("student","doc")
                .page(0,10)
                .timeout(6, TimeUnit.SECONDS)
                .submit(client)
                .hits();
        System.out.println("hits:"+hits);
        return hits;
    }

    @RequestMapping("mq/add")
    public Object mqAdd(String val){
        Map<String,Object> data=new HashMap<>();
        data.put("val",val);
        data.put("date",new Date());
        mqDirectSender.send(data);
        mqDirectSender.sendData(new Date());

        return RespData.success("mqAdd:"+val);
    }

    @SecureParams(in = false,out = true)
    @GetMapping("sec/out")
    public Object testSecureOut(String val){
        return RespData.success("secOut:"+val).add("val",val).add("date",new Date());
    }

    @SecureParams(in = true,out=false)
    @PostMapping("sec/in")
    public Object testSecureIn(@RequestBody  String val){
        return new Date();
    }

    @SecureParams
    @PostMapping("sec/full")
    public Object testSecureFull(@RequestBody TestBean bean){
        return RespData.success(bean);
    }

    @GetMapping("feign")
    public Object testFeign(String val){

        String token= ServletUtils.getRequest().getHeader("Authorization");
        System.out.println("dispatch:token:"+token);
        Object rs= userController.testSvc(val,token);
        System.out.println("dispatch:rs:"+rs);
        return rs;
    }

    @GetMapping("svc")
    public Object testSvc(String val){
        SysUser loginUser=(SysUser) SecurityUtils.getUser();
        System.out.println("auth:"+loginUser);
        Object rs=adminTestService.test(val);
        return rs;
    }

    @GetMapping("db")
    public Object testDb(String val){
        Object rs=adminTestService.testDb(val);
        return rs;
    }

    @GetMapping("db2")
    public Object testDb2(String val){
        Object rs=adminTestService.testDb2(val);
        return rs;
    }
}
