package com.boss.demo.controller;

import com.boss.demo.entity.Category;
import com.boss.demo.entity.Member;
import com.boss.demo.security.roles.IsAdmin;
import com.boss.demo.service.CategoryService;
import com.boss.demo.service.MemberService;
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

@IsAdmin
@Controller
@RequestMapping("web/category")
public class WebCategoryController {

    @Autowired
    private CategoryService categoryService;



    @GetMapping("/")
    public String list(SearchVo searchVo, Model model){
        model.addAttribute("searchVo", searchVo); //搜尋參數
        model.addAttribute("categories", categoryService.getCategories(searchVo));
        return "category/index";
    }

    @GetMapping("/add")
    public String add(Model model, HttpServletRequest request){
        model.addAttribute("category", new Category());
        return "category/add";
    }

    @PostMapping("/addSave")
    public String addSave( @Valid Category category, BindingResult result,Model model){
        try {
            categoryService.saveCategory(category);
        }catch(Exception e){
            ObjectError error = new ObjectError("globalError", "SQL語法錯誤或使用者重覆");
            result.addError(error);
        }
        if( result.hasErrors() ){
            return "category/add";
        }
        return "redirect:/web/category/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(value = "id") long id,Model model){
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "category/add";
    }

    @PostMapping("/editSave")
    public String editSave(Category category,Model model){
        categoryService.updateCategory(category);
        return "redirect:/web/category/";
    }


}
