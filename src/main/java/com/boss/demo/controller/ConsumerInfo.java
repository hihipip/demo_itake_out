package com.boss.demo.controller;

import com.boss.demo.entity.Consumer;
import com.boss.demo.handler.GlobalException;
import com.boss.demo.security.CustomUser;
import com.boss.demo.tools.CodeMsg;
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
        if( authentication!=null && authentication.getPrincipal() instanceof CustomUser ){ //如果認證不是Customer有可能anonymous
            CustomUser customUser = (CustomUser)authentication.getPrincipal();
            long consumerId = customUser.getId();
            return consumerId;
        }
        return 0;
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
                throw new GlobalException(new CodeMsg(1000,"轉換錯誤"));
            }
        }
        return consumer;
    }

}
