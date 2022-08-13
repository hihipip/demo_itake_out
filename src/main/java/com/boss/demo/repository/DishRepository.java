package com.boss.demo.repository;

import com.boss.demo.entity.Dish;
import com.boss.demo.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository  extends JpaRepository<Dish,Long> {

    Page<Dish> findAllByNameLike(String name, Pageable pageable);

}
