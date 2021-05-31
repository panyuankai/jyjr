package com.sxzhyl.jwt.config;

import com.sxzhyl.jwt.security.SecurityAccessDeniedHandler;
import com.sxzhyl.jwt.security.SecurityAuthenticationEntryPoint;
import com.sxzhyl.jwt.security.SecurityAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private SecurityAccessDeniedHandler securityAccessDeniedHandler;

    @Resource
    private SecurityAuthenticationEntryPoint securityAuthenticationEntryPoint;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private SecurityAuthenticationTokenFilter securityAuthenticationTokenFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        // 设置UserDetailsService
        // 使用BCrypt进行加密
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    //    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config = httpSecurity
//                // 禁用CSRF
//                .csrf().disable()
//                .exceptionHandling()
//                //认证失败处理
//                .authenticationEntryPoint(securityAuthenticationEntryPoint)
//                //无权限处理
//                .accessDeniedHandler(securityAccessDeniedHandler).and()
//                // 不创建session 使用token不需要session
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authorizeRequests()
//                // 登录url不验证
//                .antMatchers("/login").permitAll()
//                // OPTIONS请求不验证
//                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
//        // 剩下所有请求都需要认证
//        config.anyRequest().authenticated();
//        // 禁用缓存
//        httpSecurity.headers().cacheControl();
//        // 添加JWT filter
//        httpSecurity.addFilterBefore(securityAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
//    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                //跨域
                cors().configurationSource(CorsConfigurationSource())
                .and()
                // 禁用CSRF
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                //认证失败处理
                .authenticationEntryPoint(securityAuthenticationEntryPoint)
                //无权限处理
                .accessDeniedHandler(securityAccessDeniedHandler).and()
                // 不创建session 使用token不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // 登录url不验证
                .antMatchers("/login","/test").permitAll()
                // OPTIONS请求不验证
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 剩下所有请求都需要认证
                .anyRequest().authenticated()
                .and()
                //添加JWT filter  验证token
                .addFilterBefore(securityAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                //只能允许表单登录
//                .formLogin().permitAll()
//                .and()
                //添加JWT filter  验证token
//                .addFilterBefore(securityAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                // 禁用缓存
                .headers().cacheControl();
        // 添加JWT filter
//        httpSecurity.addFilterBefore(securityAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /*跨域原*/
    private CorsConfigurationSource CorsConfigurationSource() {
        CorsConfigurationSource source =   new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");    //同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
        corsConfiguration.addAllowedHeader("*");//header，允许哪些header，本案中使用的是token，此处可将*替换为token；
        corsConfiguration.addAllowedMethod("*");    //允许的请求方法，PSOT、GET等
        ((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**",corsConfiguration); //配置允许跨域访问的url
        return source;
    }

}
