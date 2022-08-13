package com.boss.demo.service.impl;

import com.boss.demo.entity.Category;
import com.boss.demo.entity.Dish;
import com.boss.demo.entity.DishFlavor;
import com.boss.demo.repository.DishFlavorRepository;
import com.boss.demo.repository.DishRepository;
import com.boss.demo.service.DishService;
import com.boss.demo.vo.DishVo;
import com.boss.demo.vo.SearchVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private DishFlavorRepository dishFlavorRepository;

    @Override
    @Transactional
    public DishVo saveDishWithFlavor(DishVo dishVo) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishVo,dish);
        dish = dishRepository.save(dish);
        ArrayList<DishFlavor> flavors = dishVo.getDishFlavors();
        Dish finalDish = dish;
        flavors.stream().map((item)->{
            if( item.getName()==null ) return null;
            item.setDish_id(finalDish.getId());
            return item;
        }).collect(Collectors.toList());
        flavors.stream().filter(item-> item!=null ).collect(Collectors.toList());
        dishFlavorRepository.saveAll(flavors);
        return null;
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
        Page<Dish> dishs = dishRepository.findAllByNameLike("%"+searchName+"%",sortedBy);
        Page<DishVo> dishVos = null;

        BeanUtils.copyProperties(dishs,dishVos);
        dishs.getContent().stream().map((dish)->{
            DishVo dishVo = null;
            BeanUtils.copyProperties(dishVo,dish);
            dishVos.getContent().add(dishVo);
            return dish;
        }).collect(Collectors.toList());


        return dishVos;


        //dishFlavorRepository.findAllByDishId



    }
}
