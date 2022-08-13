package com.boss.demo.tools;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Items {
    private int code;
    private String value;
    private String message;

    public interface MemberStatus{
        public static final int USER=0; //使用者
        public static final int MANAGER=1; //管理者
        public static final int ADMIN=2; //最高管理者
    }




}
