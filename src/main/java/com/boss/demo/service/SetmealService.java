package com.boss.demo.service;

import com.boss.demo.vo.DishVo;
import com.boss.demo.vo.SearchVo;
import com.boss.demo.vo.SetmealVo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SetmealService {

    SetmealVo saveSetmealWithDish(SetmealVo setmealVo);
    Page<SetmealVo> getAllSetmeal(SearchVo searchVo);
    List<SetmealVo> getAll(SearchVo searchVo);
    SetmealVo getSetmealWithDish(long id);
    SetmealVo update(SetmealVo setmealVo,Long setmealId);

}
