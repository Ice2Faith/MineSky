package com.i2f.security.core.service.impl;

import com.i2f.common.data.ISysUser;
import com.i2f.common.data.LoginUser;
import com.i2f.security.core.service.ISysPermissionService;
import com.i2f.security.core.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author ltb
 * @date 2021/8/31
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        ISysUser user = userService.getUserByUserName(username);
        if (StringUtils.isEmpty(user))
        {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(ISysUser user)
    {
        return new LoginUser(user, permissionService.getUserPermissions(user));
    }
}
