package com.boss.demo.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

    public static boolean isMobile(String s){
        Pattern p = Pattern.compile("^\\d{10}$");
        Matcher m = p.matcher(s);
        return (m.matches());
    }


}
