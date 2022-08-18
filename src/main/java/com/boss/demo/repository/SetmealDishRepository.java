package com.boss.demo.repository;

import com.boss.demo.entity.DishFlavor;
import com.boss.demo.entity.SetmealDish;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetmealDishRepository extends JpaRepository<SetmealDish,Long>{

    List<SetmealDish> findBySetmealId(long setmealId);

    void deleteBySetmealId(Long id);

}
