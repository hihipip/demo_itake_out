package com.boss.demo.service.impl;

import com.boss.demo.entity.Category;
import com.boss.demo.entity.Member;
import com.boss.demo.repository.CategoryRepository;
import com.boss.demo.service.CategoryService;
import com.boss.demo.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.getReferenceById(id);
    }

    @Override
    public Page<Category> getCategories(SearchVo searchVo) {
        int page = searchVo.getPage();
        if( page<0 ) page = 0;
        Pageable sortedBy = PageRequest.of(page, 12, Sort.by(Sort.Direction.ASC,"sort"));
        if( searchVo.getSortBy()!=null && !searchVo.getSortBy().isEmpty() ){
            sortedBy = PageRequest.of(page, 12, Sort.by(Sort.Direction.ASC,searchVo.getSortBy()));
        }
        String searchName = searchVo.getSearchName()!=null ? searchVo.getSearchName() : "";
        Page<Category> list = categoryRepository.findAllByNameLike("%"+searchName+"%",sortedBy);
        return list;
    }

    @Override
    public Category updateCategory(Category category) {
        Category dbMember = categoryRepository.findById(category.getId()).get();
        category.setCreateTime(dbMember.getCreateTime());
        category.setCreateUser(dbMember.getCreateUser());
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAll() {
        Sort sort = Sort.by(Sort.Direction.ASC,"sort");
        List<Category> list = categoryRepository.findAll(sort);
        return list;
    }
}
