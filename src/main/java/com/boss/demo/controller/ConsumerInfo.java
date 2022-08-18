package com.boss.demo.controller;

import com.boss.demo.entity.Consumer;
import com.boss.demo.handler.MyException;
import com.boss.demo.security.CustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ConsumerInfo {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public long getLoginConsumerId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUser customUser = (CustomUser)authentication.getPrincipal();
        long consumerId = customUser.getId();
        return consumerId;
    }

    public Consumer getConsumer(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUser customUser = (CustomUser)authentication.getPrincipal();
        long userId = customUser.getId();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = (String)redisTemplate.opsForValue().get("consumer_"+userId);
        Consumer consumer = null;
        if( json!=null ){
            try{
                consumer = objectMapper.readValue(json, Consumer.class);
            }catch(Exception e){
                throw new MyException(100,"轉換錯誤");
            }
        }
        return consumer;
    }

}
