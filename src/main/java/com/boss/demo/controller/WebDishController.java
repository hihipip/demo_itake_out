package com.boss.demo.controller;

import com.boss.demo.entity.Category;
import com.boss.demo.entity.DishFlavor;
import com.boss.demo.entity.Member;
import com.boss.demo.security.roles.IsAdmin;
import com.boss.demo.service.CategoryService;
import com.boss.demo.service.DishService;
import com.boss.demo.service.MemberService;
import com.boss.demo.vo.DishVo;
import com.boss.demo.vo.SearchVo;
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
@RequestMapping("web/dish")
public class WebDishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/add")
    public String add(Model model, HttpServletRequest request){
        this.setModel(model,null);
        return "dish/add";
    }

    @PostMapping("/addSave")
    public String addSave(@Valid DishVo dishVo, BindingResult result,Model model){
        try {
            dishService.saveDishWithFlavor(dishVo);
        }catch(Exception e){
            e.printStackTrace();
            ObjectError error = new ObjectError("globalError", "SQL語法錯誤");
            result.addError(error);
        }
        if( result.hasErrors() ){
            this.setModel(model,dishVo);
            return "dish/add";
        }
        return "redirect:/web/dish/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(value = "id") long id, Model model){
        DishVo dishVo = dishService.getDishWithFlavor(id);
        this.setModel(model,dishVo);
        return "dish/add";
    }

    @PostMapping("/editSave")
    public String editSave(@Valid DishVo dishVo,Model model){
        dishService.update(dishVo,dishVo.getId());
        return "redirect:/web/dish/";
    }



    @GetMapping("/")
    public String list(SearchVo searchVo, Model model){
        model.addAttribute("searchVo", searchVo); //搜尋參數
        model.addAttribute("dishVos", dishService.getAllDish(searchVo));
        model.addAttribute("memberService", memberService);

        return "dish/index";
    }

    private void setModel(Model model,DishVo dishVo){
        List<Category> categoryList = categoryService.getAll(1);
        model.addAttribute("categories", categoryList);
        if( dishVo==null ){
            dishVo = new DishVo();
            ArrayList<DishFlavor> dishFlavors = new ArrayList<>();
            dishVo.setDishFlavors(dishFlavors);
        }
        model.addAttribute("dishVo", dishVo);
        //thymleaf
        model.addAttribute("memberService", memberService);
    }

    @Autowired
    MemberService memberService;



}
