package com.boss.demo.service.impl;

import com.boss.demo.entity.Category;
import com.boss.demo.entity.Dish;
import com.boss.demo.entity.DishFlavor;
import com.boss.demo.entity.Member;
import com.boss.demo.repository.CategoryRepository;
import com.boss.demo.repository.DishFlavorRepository;
import com.boss.demo.repository.DishRepository;
import com.boss.demo.service.DishService;
import com.boss.demo.vo.DishVo;
import com.boss.demo.vo.SearchVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private DishFlavorRepository dishFlavorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * 新增
     * @param dishVo
     * @return
     */
    @Override
    @Transactional
    public DishVo saveDishWithFlavor(DishVo dishVo) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishVo,dish);
        dish = dishRepository.save(dish);
        List<DishFlavor> flavors = dishVo.getDishFlavors();
        Dish finalDish = dish;
        flavors.stream().map((item)->{
            if( item.getName()==null ) return null;
            item.setDishId(finalDish.getId());
            return item;
        }).collect(Collectors.toList());
        //移除null的
        flavors.stream().filter(item-> item!=null ).collect(Collectors.toList());
        dishFlavorRepository.saveAll(flavors);
        return dishVo;
    }

    /**
     * 更新
     * @param dishVo
     * @param dishId
     * @return
     */
    @Override
    @Transactional
    public DishVo update(DishVo dishVo,Long dishId){
        Dish dbDish = dishRepository.getReferenceById(dishId);
        dishVo.setCreateTime(dbDish.getCreateTime());
        dishVo.setCreateUser(dbDish.getCreateUser());

        //更新Dish表
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishVo,dish);
        dish = dishRepository.save(dish);
        //刪除舊的Flavor
        dishFlavorRepository.deleteByDishId(dish.getId());
        //加入新的Flavor
        List<DishFlavor> flavors = dishVo.getDishFlavors();
        Dish finalDish = dish;
        flavors.stream().map((item)->{
            if( item.getName()==null ) return null;
            item.setDishId(finalDish.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorRepository.saveAll(flavors);
        return dishVo;

    }

    @Override
    public Page<DishVo> getAllDish(SearchVo searchVo) {

        int page = searchVo.getPage();
        if( page<0 ) page = 0;
        Pageable sortedBy = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC,"sort"));
        if( searchVo.getSortBy()!=null && !searchVo.getSortBy().isEmpty() ){
            sortedBy = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC,searchVo.getSortBy()));
        }
        String searchName = searchVo.getSearchName()!=null ? searchVo.getSearchName() : "";
        Page<Dish> dishPages = dishRepository.findAllByNameLike("%"+searchName+"%",sortedBy);

        //將Dish轉DishVo
        Page<DishVo> dishVoPages = dishPages.map(dish -> {
            DishVo dishVo = new DishVo();
            BeanUtils.copyProperties(dish,dishVo);
            Category category = categoryRepository.getReferenceById(dish.getCategoryId());
            if( category!=null ){
                dishVo.setCategory_name(category.getName());
            }
            //再抓另一個表
            //List<DishFlavor> dishFlavors = dishFlavorRepository.findByDishId(dish.getId());
            //dishVo.setDishFlavors(dishFlavors);
            return dishVo;
        });
        return dishVoPages;
    }

    public DishVo getDishWithFlavor(long id){
        Dish dish = dishRepository.getReferenceById(id);
        if( dish==null ) return null;
        DishVo dishVo = new DishVo();
        BeanUtils.copyProperties(dish,dishVo);
        List<DishFlavor> dishFlavors = dishFlavorRepository.findByDishId(dish.getId());
        dishVo.setDishFlavors(dishFlavors);
        Category category = categoryRepository.getReferenceById(dish.getCategoryId());
        dishVo.setCategory_name(category.getName());
        return dishVo;

    }

}
