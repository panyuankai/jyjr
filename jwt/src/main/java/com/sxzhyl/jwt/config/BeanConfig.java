package com.sxzhyl.jwt.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.sxzhyl.jwt.security.SecurityAuthenticationTokenFilter;

/**
 * bean
 * @author qbb
 * 2021/5/24
 */
@Component
public class BeanConfig {
    @Bean
    public FilterRegistrationBean registration(SecurityAuthenticationTokenFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }

    /**
     * 使用BCrypt进行加密
     *
     * @return {@link PasswordEncoder}
     * @author LCheng
     * @date 2020/11/26 17:57
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
