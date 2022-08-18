package com.boss.demo.handler;

import com.boss.demo.tools.R;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestExceptionHandler {


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public R exception(AccessDeniedException e) {
        e.printStackTrace();
        return R.error().message("您沒有權限");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("全局異常處理");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R error(HttpRequestMethodNotSupportedException e){
        e.printStackTrace();
        return R.error().message("不支援這個方法"+e.getMethod());
    }



}
