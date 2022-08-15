package com.boss.demo.controller;

import com.boss.demo.entity.Category;
import com.boss.demo.entity.Dish;
import com.boss.demo.entity.DishFlavor;
import com.boss.demo.entity.SetmealDish;
import com.boss.demo.security.roles.IsAdmin;
import com.boss.demo.service.CategoryService;
import com.boss.demo.service.DishService;
import com.boss.demo.service.SetmealService;
import com.boss.demo.vo.DishVo;
import com.boss.demo.vo.SetmealVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@IsAdmin
@Controller
@RequestMapping("web/setmeal")
public class WebSetmealController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;


    @GetMapping("/add")
    public String add(Model model, HttpServletRequest request){
        this.setModel(model,null);
        return "setmeal/add";
    }

    @PostMapping("/addSave")
    public String addSave(@Valid SetmealVo setmealVo, BindingResult result, Model model){
        try {
            setmealService.saveSetmealWithDish(setmealVo);
        }catch(Exception e){
            e.printStackTrace();
            ObjectError error = new ObjectError("globalError", "SQL語法錯誤");
            result.addError(error);
        }
        if( result.hasErrors() ){
            this.setModel(model,setmealVo);
            return "dish/add";
        }
        return "redirect:/web/dish/";
    }


    private void setModel(Model model, SetmealVo setmealVo){
        List<Category> categoryList = categoryService.getAll(1);
        model.addAttribute("categories", categoryList);
        if( setmealVo==null ){
            setmealVo = new SetmealVo();
            ArrayList<SetmealDish> setmealDishes = new ArrayList<>();
            setmealVo.setSetmealDishes(setmealDishes);

        }
        model.addAttribute("setmealVo", setmealVo);
    }


}
