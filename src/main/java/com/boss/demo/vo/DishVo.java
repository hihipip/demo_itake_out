package com.boss.demo.vo;

import com.boss.demo.entity.Dish;
import com.boss.demo.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;

@Data
public class DishVo extends Dish {
    private ArrayList<DishFlavor> dishFlavors;
    private String category_name;
}
