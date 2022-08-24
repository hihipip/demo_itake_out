package com.boss.demo.apicontroller;

import com.boss.demo.annotation.AccessLimit;
import com.boss.demo.entity.Category;
import com.boss.demo.entity.Member;
import com.boss.demo.security.roles.IsAdmin;
import com.boss.demo.service.CategoryService;
import com.boss.demo.tools.R;
import com.boss.demo.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@IsAdmin
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    @AccessLimit(maxValue = 5, needLogin = true)
    @GetMapping
    public R list(SearchVo searchVo){
        List<Category> categories = categoryService.getAll(searchVo.getCategoryId());
        return R.ok().data("categories",categories);
    }

    @PostMapping
    public R save(@Valid @RequestBody Category category){
        category = this.categoryService.saveCategory(category);
        return R.ok().data("category",category);
    }

    @PutMapping("/{id}")
    public R update(@Valid @RequestBody Category category,@PathVariable(value = "id") long categoryId){
        category.setId(categoryId);
        category = this.categoryService.updateCategory(category);
        return R.ok().data("category",category);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable(value = "id") long categoryId){
        this.categoryService.deleteById(categoryId);
        return R.ok();
    }



}
