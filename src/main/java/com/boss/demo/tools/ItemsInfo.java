package com.boss.demo.tools;

import java.util.ArrayList;

public class ItemsInfo {
    public static ArrayList<Items> getMemberRole() {
        ArrayList<Items> list = new ArrayList<Items>();
        Items status = new Items(Items.MemberStatus.USER, "使用者", "ROLE_USER");
        list.add(status);
        status = new Items(Items.MemberStatus.MANAGER, "管理者", "ROLE_MANAGER");
        list.add(status);
        status = new Items(Items.MemberStatus.ADMIN, "最高管理者", "ROLE_ADMIN");
        list.add(status);

        return list;
    }
    public static String getMemberRoleStr(int val) {
        ArrayList<Items> list = getMemberRole();
        for (Items status : list) {
            if (status.getCode() == val)
                return status.getValue();
        }
        return "";
    }

    public static String getMemberRoleMessage(int val) {
        ArrayList<Items> list = getMemberRole();
        for (Items status : list) {
            if (status.getCode() == val)
                return status.getMessage();
        }
        return "";
    }



    public static ArrayList<Items> getDishFlavorChoices() {
        ArrayList<Items> list = new ArrayList<Items>();
        Items status = new Items(0, "辣度", "大辣、中辣、小辣");
        list.add(status);
        status = new Items(1, "酸度", "大酸、中酸、小酸");
        list.add(status);
        status = new Items(2, "甜度", "大甜、中甜、小甜");
        list.add(status);

        return list;
    }






    public static String toUpperCase(String s){
        return s.toLowerCase();
    }

}
