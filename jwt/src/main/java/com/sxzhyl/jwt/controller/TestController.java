package com.sxzhyl.jwt.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import com.sxzhyl.jwt.utils.JwtUtil;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Slf4j
@RestController
public class TestController {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        //验证用户信息
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        //获取存储的信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
        User user = (User) authentication.getPrincipal();
        return jwtUtil.generate(user);
    }

    @RequestMapping(value = "/test")
    @ResponseBody
    public String test() {
        return "不需要验证";
    }

    @RequestMapping(value = "/yz")
    @ResponseBody
    public String yz() {
        return "验证";
    }

}
