package com.boss.demo.service.impl;

import com.boss.demo.entity.*;
import com.boss.demo.handler.GlobalException;
import com.boss.demo.repository.SetmealDishRepository;
import com.boss.demo.repository.SetmealRepository;
import com.boss.demo.service.CategoryService;
import com.boss.demo.service.SetmealService;
import com.boss.demo.tools.CodeMsg;
import com.boss.demo.vo.DishVo;
import com.boss.demo.vo.SearchVo;
import com.boss.demo.vo.SetmealVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDishRepository setmealDishRepository;
    @Autowired
    private SetmealRepository setmealRepository;
    @Autowired
    private CategoryService categoryService;

    @Override
    @Transactional
    @CacheEvict(value="setmealVoCache",allEntries = true)
    public SetmealVo saveSetmealWithDish(SetmealVo setmealVo) {
        if( setmealVo.getSetmealDishes()==null ){
            throw new GlobalException(CodeMsg.CHOICE_FLAVOR_ERROR);
        }

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
        //??????null???
        setmealDishes.stream().filter(item-> item!=null ).collect(Collectors.toList());
        setmealDishRepository.saveAll(setmealDishes);
        return setmealVo;
    }

    @Override
    public Page<SetmealVo> getAllSetmeal(SearchVo searchVo) {
        int page = searchVo.getPage();
        if( page<0 ) page = 0;
        Pageable sortedBy = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC,"id"));
        if( searchVo.getSortBy()!=null && !searchVo.getSortBy().isEmpty() ){
            sortedBy = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC,searchVo.getSortBy()));
        }
        String searchName = searchVo.getSearchName()!=null ? searchVo.getSearchName() : "";
        Page<Setmeal> setmealPages = setmealRepository.findAllByNameLike("%"+searchName+"%",sortedBy);

        //???Dish???DishVo
        Page<SetmealVo> setmealVoPages = setmealPages.map(setmeal -> {
            SetmealVo setmealVo = new SetmealVo();
            BeanUtils.copyProperties(setmeal,setmealVo);
            Category category = categoryService.getCategoryById(setmeal.getCategoryId());
            if( category!=null ){
                setmealVo.setCategory_name(category.getName());
            }
            //??????????????????
            //List<DishFlavor> dishFlavors = dishFlavorRepository.findByDishId(dish.getId());
            //dishVo.setDishFlavors(dishFlavors);
            return setmealVo;
        });
        return setmealVoPages;

    }

    @Cacheable(value="setmealVoCache",key = "'setmeal_' + #searchVo.categoryId + '_' + #searchVo.searchName")
    public List<SetmealVo> getAll(SearchVo searchVo){
        List<Setmeal>  setmeals = setmealRepository.findAllByCategoryId(searchVo.getCategoryId(),Sort.by(Sort.Direction.ASC, "id"));
        List<SetmealVo> setmealVos = setmeals.stream().map(setmeal -> {
            SetmealVo setmealVo = new SetmealVo();
            BeanUtils.copyProperties(setmeal,setmealVo);
            Category category = categoryService.getCategoryById(setmeal.getCategoryId());
            setmealVo.setCategory_name(category.getName());
            setmealVo.setSetmealDishes(setmealDishRepository.findBySetmealId(setmeal.getId()));
            return setmealVo;
        }).collect(Collectors.toList());
        return setmealVos;
    }




    @Override
    @Cacheable(value="setmealVoCache",key="'setmeal_'+#id")
    public SetmealVo getSetmealWithDish(long id) {
        Setmeal setmeal = setmealRepository.getReferenceById(id);
        if( setmeal == null ) return null;
        SetmealVo setmealVo = new SetmealVo();
        BeanUtils.copyProperties(setmeal,setmealVo);
        List<SetmealDish> setmealDishes = setmealDishRepository.findBySetmealId(setmeal.getId());
        setmealVo.setSetmealDishes(setmealDishes);
        Category category = categoryService.getCategoryById(setmeal.getCategoryId());
        setmealVo.setCategory_name(category.getName());
        return setmealVo;
    }

    @Override
    @Transactional
    @CacheEvict(value="setmealVoCache",allEntries = true)
    public SetmealVo update(SetmealVo setmealVo, Long setmealId) {
        Setmeal dbSetmeal = setmealRepository.getReferenceById(setmealId);
        setmealVo.setCreateTime(dbSetmeal.getCreateTime());
        setmealVo.setCreateUser(dbSetmeal.getCreateUser());
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealVo,setmeal);
        setmeal = setmealRepository.save(setmeal);
        setmealDishRepository.deleteBySetmealId(setmeal.getId());

        List<SetmealDish> setmealDishes = setmealVo.getSetmealDishes();
        Setmeal finalSetmeal = setmeal;
        setmealDishes.stream().map((item)->{
            if( item.getName()==null ) return null;
            item.setSetmealId(finalSetmeal.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishes.stream().filter(item->item!=null).collect(Collectors.toList());

        setmealDishRepository.saveAll(setmealDishes);
        return setmealVo;
    }
}
