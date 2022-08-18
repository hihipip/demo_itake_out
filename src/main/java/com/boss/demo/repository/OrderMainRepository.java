package com.boss.demo.repository;

import com.boss.demo.entity.OrderMain;
import com.boss.demo.entity.ShopCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderMainRepository extends JpaRepository<OrderMain,Long> {

    List<OrderMain> findAllByConsumerId(long userId);

}
