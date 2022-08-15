package com.boss.demo.service.impl;

import com.boss.demo.entity.Dish;
import com.boss.demo.entity.DishFlavor;
import com.boss.demo.entity.Setmeal;
import com.boss.demo.entity.SetmealDish;
import com.boss.demo.repository.SetmealDishRepository;
import com.boss.demo.repository.SetmealRepository;
import com.boss.demo.service.SetmealService;
import com.boss.demo.vo.SearchVo;
import com.boss.demo.vo.SetmealVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDishRepository setmealDishRepository;
    @Autowired
    private SetmealRepository setmealRepository;

    @Override
    @Transactional
    public SetmealVo saveSetmealWithDish(SetmealVo setmealVo) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealVo,setmeal);
        setmeal = setmealRepository.save(setmeal);
        List<SetmealDish> setmealDishes = setmealVo.getSetmealDishes();
        Setmeal finalSetmeal = setmeal;
        setmealDishes.stream().map((item)->{
            if( item.getName()==null ) return null;
            item.setSetmealId(finalSetmeal.getId());
            return item;
        }).collect(Collectors.toList());
        //移除null的
        setmealDishes.stream().filter(item-> item!=null ).collect(Collectors.toList());
        setmealDishRepository.saveAll(setmealDishes);
        return setmealVo;
    }

    @Override
    public Page<SetmealVo> getAllSetmeal(SearchVo searchVo) {
        return null;
    }

    @Override
    public SetmealVo getSetmealWithDish(long id) {
        return null;
    }

    @Override
    public SetmealVo update(SetmealVo setmealVo, Long dishId) {
        return null;
    }
}
