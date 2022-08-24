package com.boss.demo.controller;

import com.boss.demo.security.CustomUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;



@Component
public class MemberInfo {

    /**
     * 取得目前登入者的ID
     * @return
     */
    public long getLoginMemberId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if( authentication!=null && authentication.getPrincipal() instanceof CustomUser){ //如果認證不是Customer有可能anonymous
            CustomUser customUser = (CustomUser)authentication.getPrincipal();
            long memberId = customUser.getId();
            return memberId;
        }
        return 0;
    }
}
