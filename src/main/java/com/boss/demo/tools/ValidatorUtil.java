package com.boss.demo.tools;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

    public static boolean isMobile(String s){
        Pattern p = Pattern.compile("^\\d{10}$");
        Matcher m = p.matcher(s);
        return (m.matches());
    }

    public static String getRnd(){
        Random rand = new Random(); //instance of random class
        int rnd = rand.nextInt(900000);
        return (rnd + 100000)+"";
    }


}
