package com.boss.demo.vo;

import com.boss.demo.entity.Dish;
import com.boss.demo.entity.DishFlavor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishVo extends Dish implements Serializable {
    private List<DishFlavor> dishFlavors;
    private String category_name;
}
