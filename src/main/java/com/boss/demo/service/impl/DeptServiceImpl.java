package com.boss.demo.service.impl;

import com.boss.demo.entity.Dept;
import com.boss.demo.entity.Dish;
import com.boss.demo.repository.DeptRepository;
import com.boss.demo.service.DeptService;
import com.boss.demo.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptRepository deptRepository;

    @Override
    public Page<Dept> getAllDept(SearchVo searchVo) {
        int page = searchVo.getPage();
        if( page<0 ) page = 0;
        Pageable sortedBy = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC,"id"));
        Page<Dept> deptPage = deptRepository.findAll(sortedBy);
        return deptPage;
    }

    @Override
    public Dept saveDept(Dept dept){
        dept = this.deptRepository.save(dept);
        return dept;
    }

    @Override
    public List<Dept> getAll() {
        return this.deptRepository.findAll();
    }
}
