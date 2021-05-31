package com.sxzhyl.jwt.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.sxzhyl.jwt.utils.JwtUtil;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token验证的过滤器
 * @author Administrator
 * 2021/5/24
 */
@Slf4j
@Component
public class SecurityAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtil.getToken(httpServletRequest);
        //判断token是否有效
        if (StringUtils.hasText(token)) {
            //创建AuthenticationToken
            UsernamePasswordAuthenticationToken authentication = jwtUtil.getAuthentication(token);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else {
            log.debug("token无效。");
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
