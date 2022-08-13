package com.boss.demo.repository;

import com.boss.demo.entity.Category;
import com.boss.demo.entity.Dish;
import com.boss.demo.entity.DishFlavor;
import com.boss.demo.entity.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DishFlavorRepository extends JpaRepository<DishFlavor,Long>{
    List<DishFlavor> findByDishId(long dishId);
    void deleteByDishId(long dishId);
}
