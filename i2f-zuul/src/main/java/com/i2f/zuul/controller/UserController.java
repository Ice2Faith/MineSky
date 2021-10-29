package com.i2f.zuul.controller;

import com.i2f.common.data.LoginUser;
import com.i2f.common.data.RespData;
import com.i2f.security.core.components.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ltb
 * @date 2021/8/31
 */
@RestController
public class UserController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping("/login")
    private RespData login(String userName,String password){
        // 用户验证
        Authentication authentication = null;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        LoginUser loginUser=(LoginUser) authentication.getPrincipal();
        String token=tokenService.createToken(loginUser);
        return RespData.success(token);
    }

}
