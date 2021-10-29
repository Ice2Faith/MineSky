package com.i2f.service.user.impl;

import com.i2f.redis.RedisCache;
import com.i2f.security.core.service.ITokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author ltb
 * @date 2021/8/31
 */
@Component
public class TokenRedisCacheImpl implements ITokenCache {

    @Autowired
    private RedisCache redisCache;

    @Override
    public <T> void setCacheObject(String key, T value) {
        redisCache.setCacheObject(key,value);
    }

    @Override
    public <T> void setCacheObject(String key, T value, Integer timeout, TimeUnit timeUnit) {
       redisCache.setCacheObject(key,value,timeout,timeUnit);
    }

    @Override
    public <T> T getCacheObject(String key) {
        return redisCache.getCacheObject(key);
    }

    @Override
    public boolean deleteObject(String key) {
        return redisCache.deleteObject(key);
    }
}
