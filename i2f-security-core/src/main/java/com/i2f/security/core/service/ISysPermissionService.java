package com.i2f.security.core.service;

import com.i2f.common.data.ISysUser;

import java.util.Set;

/**
 * @author ltb
 * @date 2021/8/31
 */
public interface ISysPermissionService
{
    /**
     * 获取用户的权限信息
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    Set<String> getUserPermissions(ISysUser user);
}
