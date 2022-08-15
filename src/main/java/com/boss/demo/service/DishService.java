package com.boss.demo.service;

import com.boss.demo.entity.Dish;
import com.boss.demo.entity.Member;
import com.boss.demo.vo.DishVo;
import com.boss.demo.vo.SearchVo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DishService {

    DishVo saveDishWithFlavor(DishVo dishVo);
    Page<DishVo> getAllDish(SearchVo searchVo);

    List<DishVo> getAll(SearchVo searchVo);


    DishVo getDishWithFlavor(long id);

    DishVo update(DishVo dishVo,Long dishId);


}
