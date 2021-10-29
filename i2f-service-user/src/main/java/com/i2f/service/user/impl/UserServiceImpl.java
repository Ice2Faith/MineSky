package com.i2f.service.user.impl;

import com.i2f.common.data.ISysUser;
import com.i2f.service.user.IUserService;
import com.i2f.service.user.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ltb
 * @date 2021/9/2
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public ISysUser getUserByUserName(String userName) {
        SysUser user=new SysUser();
        user.setUserName(userName);
        String truePass="123456";
        String pass=encoder.encode(truePass);
        user.setPassword(pass);
        return user;
    }

    @Override
    public Set<String> getUserPermissions(ISysUser user) {
        Set<String> ret=new HashSet<>();
        return ret;
    }
}
