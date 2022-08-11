package com.boss.demo.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyWebException extends RuntimeException{
    private int code;
    private String msg;
}
