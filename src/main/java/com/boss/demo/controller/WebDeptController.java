package com.boss.demo.controller;


import com.boss.demo.entity.Category;
import com.boss.demo.entity.Dept;
import com.boss.demo.entity.DishFlavor;
import com.boss.demo.security.roles.IsAdmin;
import com.boss.demo.service.DeptService;
import com.boss.demo.vo.DishVo;
import com.boss.demo.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
@RequestMapping("/web/dept")
public class WebDeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping("/add")
    public String add(Model model, HttpServletRequest request){
        this.setModel(model,null);
        return "dept/add";
    }

    @PostMapping("/addSave")
    public String addSave(@Valid Dept dept, BindingResult result, Model model){
        try {
            deptService.saveDept(dept);
        }catch(Exception e){
            e.printStackTrace();
            ObjectError error = new ObjectError("globalError", "SQL語法錯誤");
            result.addError(error);
        }
        if( result.hasErrors() ){
            this.setModel(model,dept);
            return "dept/add";
        }
        return "redirect:/web/dept/";
    }

    @GetMapping
    public String list(SearchVo searchVo, Model model){
        model.addAttribute("searchVo", searchVo); //搜尋參數
        model.addAttribute("depts", deptService.getAllDept(searchVo));
        return "dept/index";
    }


    private void setModel(Model model, Dept dept){
        if( dept==null ){
            dept = new Dept();
        }
        model.addAttribute("dept", dept);
    }

}
