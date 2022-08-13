package com.boss.demo.vo;

import com.boss.demo.entity.Dish;
import com.boss.demo.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishVo extends Dish {
    private List<DishFlavor> dishFlavors;
    private String category_name;
}
