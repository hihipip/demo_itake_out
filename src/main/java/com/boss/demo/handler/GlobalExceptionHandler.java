package com.boss.demo.handler;

import com.boss.demo.handler.MyException;
import com.boss.demo.tools.R;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {


    //@ResponseBody
    @ExceptionHandler(SQLException.class)
    public ModelAndView handlerConstraintViolationException(Exception e) {
        e.printStackTrace();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorSomething","somerror");
        modelAndView.setViewName("error");
        return modelAndView;
    }




/*
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public R error(MyException e){
        e.printStackTrace();
        return R.error().message(e.getMsg());
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("全局異常處理");
    }
*/




    // For UI Pages
    @ExceptionHandler(MyWebException.class)
    public String myWebException(MyWebException ex) {
        return "error";
    }






}
