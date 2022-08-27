package com.boss.demo.apicontroller;

import com.boss.demo.entity.Consumer;
import com.boss.demo.entity.Member;
import com.boss.demo.handler.GlobalException;
import com.boss.demo.service.MemberService;
import com.boss.demo.tools.CodeMsg;
import com.boss.demo.tools.JwtTokenUtil;
import com.boss.demo.tools.R;
import com.boss.demo.vo.LoginVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MemberService memberService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/hello")
    public R hello(@RequestParam int userId) throws InterruptedException {
        System.out.println("userId="+userId + " " + new Date());
        synchronized (lock){
            Thread.sleep(5000);
        }
        System.out.println("userId="+userId + " " + new Date());
        return R.ok();
    }


    @PostMapping("")
    public R login(@RequestBody LoginVo loginVo) throws JsonProcessingException {

        Member member = memberService.getMemberByUsername(loginVo.getUsername());
        if( member==null ){
            throw new GlobalException(CodeMsg.NOT_FOUND_ERROR);
        }
        if( !member.getPassword().equals(loginVo.getPassword()) ){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        log.info("Login Starting");

        //將登入管理者資料記錄在redis
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(member);
        redisTemplate.opsForValue().set("member_"+member.getId(),json,1, TimeUnit.DAYS);
        //產生Token
        String token = jwtTokenUtil.generateToken(member.getId(),member.getUsername(),member.getName());
        return R.ok().data("token",token).data("expired",jwtTokenUtil.getExpirationDateFromToken(token));
    }





    //測試Sync
    private Object lock = new Object();
    @GetMapping("/sync")
    public R sync(@RequestParam int userId) throws InterruptedException {
        System.out.println("userId="+userId + " " + new Date());
        synchronized (lock){
            Thread.sleep(5000);
        }
        System.out.println("userId="+userId + " " + new Date());
        return R.ok();
    }



}
