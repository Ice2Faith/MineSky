package com.i2f.service.impl;


import com.i2f.dao.AdminTestDao;
import com.i2f.dao2.NoteTestDao;
import com.i2f.service.IAdminTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ltb
 * @date 2021/8/30
 */
@Service
public class AdminTestServiceImpl implements IAdminTestService {

    private Logger logger=LoggerFactory.getLogger(AdminTestServiceImpl.class);

    @Resource
    private AdminTestDao adminTestDao;

//    @Resource
//    private NoteTestDao noteTestDao;

    @Override
    public Object test(Object o) {
        String rs="AdminTestServiceImpl:test:"+o;
        logger.info(rs);
        return rs;
    }

    @Override
    public Object testDb(Object o) {
        String rs="AdminTestServiceImpl:testDb:"+o;
        logger.info(rs);
//        return rs;
        String dbrs=adminTestDao.testDb(rs);
        return dbrs;
    }

    @Override
    public Object testDb2(String o) {
        String rs="AdminTestServiceImpl:testDb2:"+o;
        logger.info(rs);
        return rs;
//        String dbrs=noteTestDao.testNote();
//        return dbrs;
    }
}
