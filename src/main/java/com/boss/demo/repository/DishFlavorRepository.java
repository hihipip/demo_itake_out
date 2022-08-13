package com.boss.demo.repository;

import com.boss.demo.entity.Dish;
import com.boss.demo.entity.DishFlavor;
import com.boss.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishFlavorRepository extends JpaRepository<DishFlavor,Long>{

}
