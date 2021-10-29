package com.i2f.security.core.service;

import com.i2f.common.data.ISysUser;

/**
 * @author ltb
 * @date 2021/8/31
 */
public interface ISysUserService
{
    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    ISysUser getUserByUserName(String userName);


}
