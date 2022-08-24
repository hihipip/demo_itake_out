package com.boss.demo.repository;

import com.boss.demo.entity.Category;
import com.boss.demo.entity.Dept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeptRepository extends JpaRepository<Dept,Long> {

    Page<Dept> findAll(Pageable pageable);



}
