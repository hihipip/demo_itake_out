package com.boss.demo.repository;

import com.boss.demo.entity.Dish;
import com.boss.demo.entity.Setmeal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetmealRepository extends JpaRepository<Setmeal,Long> {

}
