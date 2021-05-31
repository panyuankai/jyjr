package com.sxzhyl.jwt.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * jwt配置参数
 * @author qbb
 * 2021/5/24
 */
@Data
@ConfigurationProperties(prefix = "jwt")
@Configuration
public class JwtProperties {

    /**
     * http请求头
     */
    @Value("${jwt.start-with}")
    private String header;

    /**
     * token起始标识
     */
    @Value("${jwt.start-with}")
    private String startWith;

    /**
     * token秘钥
     */
    @Value("${jwt.secret-key}")
    private String secretKey;

    /**
     * token过期时间 单位/秒
     */
    @Value("${jwt.validate-second}")
    private Long validateSecond;
}
