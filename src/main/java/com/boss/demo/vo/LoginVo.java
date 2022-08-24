package com.boss.demo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginVo implements Serializable {
    private String username;
    private String password;
}
