package com.example.redis.demo.controller;

import com.example.redis.demo.config.RedisConfig;
import com.example.redis.demo.config.RedisProperties;
import com.example.redis.demo.service.JedisClientCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    private RedisProperties redisProperties;

    @Autowired
    private RedisConfig redisConfig;

    @Autowired
    private JedisClientCluster jedisClientCluster;

    @GetMapping(value = "getRedisValue")
    public String getRedisValue(){
        System.out.println(redisProperties.toString());
        System.out.println(redisConfig.getJedisCluster().getClusterNodes());
        System.out.println(jedisClientCluster.get("hello"));
        jedisClientCluster.set("12","12");
        System.out.println(jedisClientCluster.get("12"));
        return jedisClientCluster.get("12");
    }

    @GetMapping(value = "getRedisValue")
    public String get(){
        jedisClientCluster.exists("hello");
        System.out.println(jedisClientCluster.get("12"));
        return jedisClientCluster.get("12");
    }



}