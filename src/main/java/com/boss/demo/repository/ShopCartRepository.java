package com.boss.demo.repository;

import com.boss.demo.entity.Dish;
import com.boss.demo.entity.ShopCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopCartRepository extends JpaRepository<ShopCart,Long> {

    List<ShopCart> findAllByUserId(long userId);

    ShopCart getByUserIdAndDishId(long userId, long dishId);

    ShopCart getByUserIdAndSetmealId(long userId, long setmealId);

    void deleteByUserId(Long id);
}
