package com.boss.demo.filter;

import com.boss.demo.annotation.AccessLimit;
import com.boss.demo.controller.ConsumerInfo;
import com.boss.demo.controller.MemberInfo;
import com.boss.demo.tools.CodeMsg;
import com.boss.demo.tools.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 * 做一個基本的限流
 */
@Component
public class MessageInterceptor implements HandlerInterceptor {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MemberInfo memberInfo;
    @Autowired
    private RedisTemplate redisTemplate;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //做一個限流的測試
        if( handler instanceof HandlerMethod ){

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if( accessLimit==null ){
                return true;
            }
            int maxValue = accessLimit.maxValue();
            int seconds = accessLimit.seconds();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();
            if( needLogin && memberInfo.getLoginMemberId()==0 ){
                render(response,CodeMsg.ACCESS_DENY_ERROR);
                return false;
            }

            key += "limit:_" + memberInfo.getLoginMemberId();
            Integer count = (Integer) redisTemplate.opsForValue().get(key);
            if( count==null ){
                redisTemplate.opsForValue().set(key,1);
                redisTemplate.expire(key,seconds, TimeUnit.SECONDS);
            } else if (count <= maxValue) {
                redisTemplate.opsForValue().increment(key);
            }
            // 超出最大访问次数限制
            else {
                render(response, CodeMsg.ACCESS_BEYOND_LIMIT);
                return false;
            }

        }
        return true;
    }

    private void render(HttpServletResponse response, CodeMsg codeMsg) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        OutputStream outputStream = response.getOutputStream();
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        String out = objectMapper.writeValueAsString(R.error(codeMsg));
        outputStream.write(out.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
    }
}
