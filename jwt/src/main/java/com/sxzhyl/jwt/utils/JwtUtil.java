package com.sxzhyl.jwt.utils;


import com.sxzhyl.jwt.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * jwt工具
 *
 * @author qbb
 * 2021/5/24
 */
@Component
@Slf4j
public class JwtUtil {

    @Autowired
    private JwtProperties jwtProperties;

    public static final String USER_KEY = "user";

    /**
     * 从request中获取token
     *
     * @param request
     * @return {@link String}
     * @author LCheng
     * @date 2020/11/26 17:15
     */
    public String getToken(HttpServletRequest request) {
        String token = "";
        String bearerToken =request.getHeader(jwtProperties.getHeader());
        //这样写的话  好像所有token都可以通过了
        String realToken=jwtProperties.getStartWith()+bearerToken;
        if (StringUtils.hasText(bearerToken) && realToken.startsWith(jwtProperties.getStartWith())) {
            token = realToken.substring(jwtProperties.getStartWith().length());
        }
        return token;
    }

    /**
     * 根据token获取AuthenticationToken
     *
     * @param token
     * @return {@link UsernamePasswordAuthenticationToken}
     * @author LCheng
     * @date 2020/11/26 18:16
     */
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = validate(token);
        if (claims == null) {
            return null;
        }

        HashMap map = (HashMap) claims.get(USER_KEY);
        Collection<? extends GrantedAuthority> authorities =
                ((List<Map<String, String>>) map.get("authorities")).stream()
                        .map(a -> new SimpleGrantedAuthority(a.get("authority")))
                        .collect(Collectors.toList());
        User principal = new User((String) map.get("username"), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    /**
     * 生成jwt
     *
     * @param user
     * @author LCheng
     * @date 2020/11/26 17:15
     */
    public String generate(User user) {
        String token = Jwts.builder()
                .claim(USER_KEY, user)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getValidateSecond() * 1000))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()).compact();
        return token;
    }


    /**
     * 校验token
     *
     * @param token
     * @return {@link java.lang.Boolean}
     * @author LCheng
     * @date 2020/11/26 17:16
     */
    public Claims validate(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
