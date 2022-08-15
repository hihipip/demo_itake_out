package com.boss.demo.repository;

import com.boss.demo.entity.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository  extends JpaRepository<Dish,Long> {

    Page<Dish> findAllByNameLike(String name, Pageable pageable);
    List<Dish> findAllByCategoryIdAndStatus(long categoryId, int status, Sort sort);

}
