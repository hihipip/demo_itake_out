package com.boss.demo.controller.front;


import com.boss.demo.entity.*;
import com.boss.demo.repository.ConsumerRepository;
import com.boss.demo.repository.ShopCartRepository;
import com.boss.demo.security.CustomUser;
import com.boss.demo.security.roles.IsUser;
import com.boss.demo.service.CategoryService;
import com.boss.demo.service.DishService;
import com.boss.demo.service.SetmealService;
import com.boss.demo.tools.R;
import com.boss.demo.vo.DishVo;
import com.boss.demo.vo.SearchVo;
import com.boss.demo.vo.SetmealVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("front")
public class FrontIndexController {

    @Autowired
    private ConsumerRepository consumerRepository;
    @Autowired
    private ShopCartRepository shopCartRepository;


    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private DishService dishService;


    @GetMapping("")
    public String index(SearchVo searchVo, Model model){
        List<Category> categoryList = categoryService.getAll(0);
        List<SetmealVo> setmealVos = setmealService.getAll(searchVo);
        List<DishVo> dishVos = dishService.getAll(searchVo);
        model.addAttribute("categories", categoryList);
        model.addAttribute("setmealVos", setmealVos);
        model.addAttribute("dishVos", dishVos);
        return "front/index";
    }


    @GetMapping("/chat")
    public String chat(){
        return "front/chat";
    }





}
