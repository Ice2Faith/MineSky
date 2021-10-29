package com.i2f.service.impl;


import com.i2f.dao.AdminTestDao;
import com.i2f.service.IAdminTestService;
import com.i2f.service.IAppTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ltb
 * @date 2021/8/30
 */
@Service
public class AppTestServiceImpl implements IAppTestService {

    private Logger logger=LoggerFactory.getLogger(AppTestServiceImpl.class);

    @Resource
    private AdminTestDao adminTestDao;

    @Override
    public Object test(Object o) {
        String rs="AppTestServiceImpl:test:"+o;
        logger.info(rs);
        return rs;
    }

    @Override
    public Object testDb(Object o) {
        String rs="AppTestServiceImpl:testDb:"+o;
        logger.info(rs);
//        return rs;
        String dbrs=adminTestDao.testDb(rs);
        return dbrs;
    }
}
