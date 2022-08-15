package com.boss.demo.vo;

import com.boss.demo.entity.Dish;
import com.boss.demo.entity.DishFlavor;
import com.boss.demo.entity.Setmeal;
import com.boss.demo.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealVo extends Setmeal {
    private List<SetmealDish> setmealDishes;
    private String category_name;
}
