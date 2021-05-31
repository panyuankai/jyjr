package com.sxzhyl.jwt.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sxzhyl.jwt.entity.TUser;
import org.springframework.security.core.userdetails.User;
import com.sxzhyl.jwt.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录认证
 *
 * @author qbb
 * 2021/5/24
 */
@Component
@Slf4j
public class SecurityUserDetailServiceImpl implements UserDetailsService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String accountName) throws UsernameNotFoundException {
//        UserDetails userDetails = null;
//        TUser user = userService.getOne(new QueryWrapper<TUser>()
//                .eq("username", accountName).last("order by id desc limit 1"));
//        if(user==null){
//            throw new UsernameNotFoundException("No user found with username: " + accountName);
//        }
//        String password=user.getPassword();
//        String role=user.getRoleId();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new User(accountName, passwordEncoder.encode("123"), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
//        return new User(accountName, passwordEncoder.encode(user.getPassword()), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
    }
}
