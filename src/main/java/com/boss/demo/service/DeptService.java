package com.boss.demo.service;

import com.boss.demo.entity.Dept;
import com.boss.demo.vo.DishVo;
import com.boss.demo.vo.SearchVo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DeptService {

    Page<Dept> getAllDept(SearchVo searchVo);
    Dept saveDept(Dept dept);
    List<Dept> getAll();
}
