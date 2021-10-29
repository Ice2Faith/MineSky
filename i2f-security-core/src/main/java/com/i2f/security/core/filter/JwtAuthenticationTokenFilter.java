package com.i2f.security.core.filter;

import com.i2f.common.data.LoginUser;
import com.i2f.common.util.SecurityUtils;
import com.i2f.security.core.components.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ltb
 * @date 2021/8/31
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter
{
    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException
    {
    	String path=request.getRequestURI();

        System.out.println("===========doFilterInternal=================");
    	LoginUser loginUser = tokenService.getLoginUser(request);
    	System.out.println("jwt:loginUser:"+loginUser);
    	Authentication auth= SecurityUtils.getAuthentication();
    	System.out.println("jwt:auth:"+auth);
        if (!StringUtils.isEmpty(loginUser) && StringUtils.isEmpty(auth))
        {
            System.out.println("jwt:bengin:verify");
            tokenService.verifyToken(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }
}
