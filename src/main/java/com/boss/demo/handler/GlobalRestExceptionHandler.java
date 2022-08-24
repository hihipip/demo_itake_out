package com.boss.demo.handler;

import com.boss.demo.tools.CodeMsg;
import com.boss.demo.tools.R;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;


@RestControllerAdvice
@Order(0)
public class GlobalRestExceptionHandler {


    /*
    @ExceptionHandler(AccessDeniedException.class)
    public R exception(AccessDeniedException e) {
        e.printStackTrace();
        return R.error().message("您沒有權限");
    }
    */


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        if( e instanceof GlobalException ){
            GlobalException ex = (GlobalException)e;
            return R.error(ex.getCodeMsg());
        } else if( e instanceof BindException){
            List<ObjectError> errors = ((BindException) e).getAllErrors();//绑定错误返回很多错误，是一个错误列表，只需要第一个错误
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return R.error(CodeMsg.BIND_ERROR).message(msg);
        } else if( e instanceof ConstraintViolationException){
            String msg = e.getMessage();
            return R.error(CodeMsg.CONSTRAINT_ERROR);
        } else if( e instanceof AccessDeniedException){
            return R.error(CodeMsg.ACCESS_DENY_ERROR);
        } else if( e instanceof HttpRequestMethodNotSupportedException){
            return R.error(CodeMsg.METHOD_ERROR); //不支援的方法
        }
        return R.error(CodeMsg.ERROR).message(e.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public R handleBindException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        return R.error(CodeMsg.ERROR).message(fieldError.getDefaultMessage());
    }





}
