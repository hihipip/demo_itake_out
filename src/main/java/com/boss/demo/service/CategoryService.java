package com.boss.demo.service;

import com.boss.demo.entity.Category;
import com.boss.demo.entity.Member;
import com.boss.demo.vo.SearchVo;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public interface CategoryService {

    Category saveCategory(Category category);
    Category getCategoryById(long id);
    Page<Category> getCategories(SearchVo searchVo);
    Category updateCategory(Category category);
    List<Category> getAll();

}
