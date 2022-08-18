package com.boss.demo.repository;

import com.boss.demo.entity.Dish;
import com.boss.demo.entity.Setmeal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetmealRepository extends JpaRepository<Setmeal,Long> {
    Page<Setmeal> findAllByNameLike(String name, Pageable pageable);
    List<Setmeal> findAllByCategoryId(long categoryId, Sort sort);
}
