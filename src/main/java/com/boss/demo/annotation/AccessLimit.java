package com.boss.demo.annotation;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface AccessLimit {

    int seconds() default 10;

    //seconds最大回應
    int maxValue();

    boolean needLogin() default true;
}
