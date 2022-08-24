package com.boss.demo.apicontroller;


import com.boss.demo.entity.Member;
import com.boss.demo.security.roles.IsAdmin;
import com.boss.demo.service.DishService;
import com.boss.demo.service.MemberService;
import com.boss.demo.tools.R;
import com.boss.demo.vo.DishVo;
import com.boss.demo.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@IsAdmin
@RestController
@RequestMapping("/api/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping
    public R list(SearchVo searchVo){
        return R.ok().data("data",dishService.getAll(searchVo));
    }

    @PostMapping
    public R save(@Valid @RequestBody DishVo dishVo){
        dishVo=dishService.saveDishWithFlavor(dishVo);
        return R.ok().data("dishVo",dishVo);
    }

    @GetMapping("/{id}")
    public R edit(@PathVariable(value = "id") long id){
        DishVo dishVo = dishService.getDishWithFlavor(id);
        return R.ok().data("dishVo",dishVo);
    }

    @PutMapping("/{id}")
    public R editSave(@Valid DishVo dishVo){
        dishService.update(dishVo,dishVo.getId());
        return R.ok().data("dishVo",dishVo);
    }



}
