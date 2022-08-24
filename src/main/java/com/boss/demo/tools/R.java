package com.boss.demo.tools;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R {
    private Boolean success;
    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    private R() {};

    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(CodeMsg.SUCCESS.getCode());
        r.setMessage(CodeMsg.SUCCESS.getMsg());
        return r;
    }

    public static R error(CodeMsg codeMsg) {
        R r = new R();
        r.setSuccess(false);
        r.setCode(codeMsg.getCode());
        r.setMessage(codeMsg.getMsg());
        return r;
    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

/*
    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }
*/
    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

}
