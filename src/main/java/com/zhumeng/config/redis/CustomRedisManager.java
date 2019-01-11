package com.zhumeng.config.redis;

import org.crazycake.shiro.RedisManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@ConfigurationProperties(prefix = "spring.redis")
@EnableRedisHttpSession
public class CustomRedisManager extends RedisManager {

}