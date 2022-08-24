package com.boss.demo.tools;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CodeMsg
{
    private int code;
    private String msg;

    public static final CodeMsg SUCCESS = new CodeMsg(0,"success");

    public static final CodeMsg ERROR = new CodeMsg(1000,"其它錯誤");

    public static final CodeMsg SERVER_ERROR = new CodeMsg(500,"伺服器出錯");
    public static final CodeMsg ACCESS_DENY_ERROR = new CodeMsg(403,"沒有權限");
    public static final CodeMsg BIND_ERROR = new CodeMsg(1001,"綁定錯誤");
    public static final CodeMsg CONSTRAINT_ERROR = new CodeMsg(1002,"參數驗證錯誤");
    public static final CodeMsg METHOD_ERROR = new CodeMsg(1003,"不支援這個方法");

    public static final CodeMsg NOT_FOUND_ERROR = new CodeMsg(1003,"查無此使用者");
    public static final CodeMsg PASSWORD_ERROR = new CodeMsg(1003,"密碼錯誤");
    public static final CodeMsg CHOICE_FLAVOR_ERROR = new CodeMsg(1003,"請選擇口味");

    public static final CodeMsg ACCESS_BEYOND_LIMIT = new CodeMsg(1003,"限制流量喔");

}
