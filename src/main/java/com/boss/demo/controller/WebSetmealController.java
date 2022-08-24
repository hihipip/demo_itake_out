package com.boss.demo.controller;

import com.boss.demo.entity.Category;
import com.boss.demo.entity.Dish;
import com.boss.demo.entity.DishFlavor;
import com.boss.demo.entity.SetmealDish;
import com.boss.demo.handler.GlobalException;
import com.boss.demo.security.roles.IsAdmin;
import com.boss.demo.service.CategoryService;
import com.boss.demo.service.DishService;
import com.boss.demo.service.SetmealService;
import com.boss.demo.vo.DishVo;
import com.boss.demo.vo.SearchVo;
import com.boss.demo.vo.SetmealVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        }catch(GlobalException e){
            ObjectError error = new ObjectError("globalError", ((GlobalException)e).getCodeMsg().getMsg());
            result.addError(error);
        }catch(Exception e){
            e.printStackTrace();
            ObjectError error = new ObjectError("globalError", "SQL語法錯誤");
            result.addError(error);
        }
        if( result.hasErrors() ){
            this.setModel(model,setmealVo);
            return "setmeal/add";
        }
        return "redirect:/web/setmeal";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(value = "id") long id, Model model){
        SetmealVo setmealVo = setmealService.getSetmealWithDish(id);
        this.setModel(model,setmealVo);
        return "setmeal/add";
    }

    @PostMapping("/editSave")
    public String editSave(@Valid SetmealVo setmealVo,Model model){
        setmealService.update(setmealVo,setmealVo.getId());
        return "redirect:/web/setmeal";
    }


    @GetMapping
    public String list(SearchVo searchVo, Model model){
        model.addAttribute("searchVo", searchVo); //搜尋參數
        model.addAttribute("setmealVos", setmealService.getAllSetmeal(searchVo));
        return "setmeal/index";
    }



    private void setModel(Model model, SetmealVo setmealVo){
        List<Category> categoryList = categoryService.getAll(2);
        model.addAttribute("categories", categoryList);
        if( setmealVo==null ){
            setmealVo = new SetmealVo();
            ArrayList<SetmealDish> setmealDishes = new ArrayList<>();
            setmealVo.setSetmealDishes(setmealDishes);
        }
        model.addAttribute("setmealVo", setmealVo);
    }


}
