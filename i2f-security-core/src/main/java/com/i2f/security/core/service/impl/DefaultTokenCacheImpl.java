package com.i2f.security.core.service.impl;


import com.i2f.security.core.service.ITokenCache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ltb
 * @date 2021/8/31
 */
//@Component
public class DefaultTokenCacheImpl implements ITokenCache {
    public class BundleData{
        private Date expire;
        private Object data;

        public BundleData(Date expire, Object data) {
            this.expire = expire;
            this.data = data;
        }

        public BundleData(long expire, Object data) {
            this.expire = new Date(new Date().getTime()+expire);
            this.data = data;
        }

        public Date getExpire() {
            return expire;
        }

        public void setExpire(Date expire) {
            this.expire = expire;
        }

        public void setExpire(long expire) {
            this.expire = new Date(new Date().getTime()+expire);
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
    private static volatile Map<String,BundleData> cache=new HashMap<>();
    @Override
    public <T> void setCacheObject(String key, T value) {
        cache.put(key,new BundleData(Integer.MAX_VALUE,value));
    }

    @Override
    public <T> void setCacheObject(String key, T value, Integer timeout, TimeUnit timeUnit) {
        cache.put(key,new BundleData(timeUnit.toMillis(timeout),value));
    }

    @Override
    public <T> T getCacheObject(String key) {
        BundleData data=cache.get(key);
        if(data==null){
            return null;
        }
        Date exp=data.getExpire();
        if(exp.before(new Date())){
            return null;
        }
        return (T)data.getData();
    }

    @Override
    public boolean deleteObject(String key) {
        if(cache.containsKey(key)){
            cache.remove(key);
        }
        return true;
    }
}
